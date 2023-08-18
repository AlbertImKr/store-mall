package com.albert.commerce.query.domain.order;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderDetails {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_detail", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderDetail> orderDetailCollections = new ArrayList<>();

    public OrderDetails(List<OrderDetail> orderDetailCollections) {
        this.orderDetailCollections = orderDetailCollections;
    }
}
