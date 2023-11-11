package com.albert.commerce.adapter.out.config.messaging.error;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorHandler {

    private static final String ERROR_TOPIC = "error-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    // consumer 에서 발생한 에러 정보만 kafka로 전송한다.
    public void handleErrorMessage(ErrorMessage errorMessage) {
        Throwable payload = errorMessage.getPayload();
        String key = getKey(payload);

        List<Header> headers = getHeaders(errorMessage.getHeaders());

        ProducerRecord<String, String> kafkaRecord = new ProducerRecord<>(
                ERROR_TOPIC,
                null,
                key,
                payload.getCause().toString(),
                headers
        );

        kafkaTemplate.send(kafkaRecord);
    }

    private static List<Header> getHeaders(Map<String, Object> headers) {
        List<Header> headersList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            if (entry.getValue() instanceof byte[]) {
                byte[] value = (byte[]) (entry.getValue());
                RecordHeader recordHeader = new RecordHeader(entry.getKey(), value);
                headersList.add(recordHeader);
            } else if (entry.getValue() != null) {
                RecordHeader recordHeader = new RecordHeader(
                        entry.getKey(),
                        entry.getValue()
                                .toString()
                                .getBytes(StandardCharsets.UTF_8)
                );
                headersList.add(recordHeader);
            }
        }
        return headersList;
    }

    private static String getKey(Object payload) {
        return String.valueOf(payload.hashCode());
    }
}
