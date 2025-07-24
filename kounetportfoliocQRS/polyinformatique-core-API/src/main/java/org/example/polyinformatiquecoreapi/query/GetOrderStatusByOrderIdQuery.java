package org.example.polyinformatiquecoreapi.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrderStatusByOrderIdQuery {
    private String orderId;
}