package com.albert.commerce.adapter.in.web;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class test {


    @Test
    void sendPostRequest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/test-client/token";
        restTemplate.postForEntity(url, null, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = "{ \"key\": \"value\" }";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        HttpHeaders headers1 = response.getHeaders();
        List<String> list = headers1.get("Location");
        System.out.println(list);

        System.out.println(response.getBody());

//
//        mockMvc.perform(post(url))
//                .andDo(print());

    }

}
