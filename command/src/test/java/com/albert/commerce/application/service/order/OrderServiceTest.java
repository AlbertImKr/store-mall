package com.albert.commerce.application.service.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.albert.commerce.application.port.out.OrderRepository;
import com.albert.commerce.application.service.ApplicationFixture;
import com.albert.commerce.application.service.exception.error.OrderNotFoundException;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @DisplayName("유저 아이디 및 주문 아이디로 오더 조회시 오더가 없으면 예외가 발생한다")
    @Test
    void get_order_by_user_id_and_order_id_test() {
        // given
        UserId userId = ApplicationFixture.getUserId();
        OrderId orderId = ApplicationFixture.getOrderId();
        given(orderRepository.findByUserIdAndOrderId(userId, orderId))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> orderService.getOrderByUserIdAndOrderId(userId, orderId))
                .isInstanceOf(OrderNotFoundException.class);
    }
}
