package com.sulek.order.service;

import com.sulek.order.entity.User;
import com.sulek.order.model.OrderDto;
import com.sulek.order.model.ProductOrderDto;
import com.sulek.order.model.response.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponseDto placeOrder(ProductOrderDto productOrder, User user);

    List<OrderDto> checkOrderByStatus(String orderStatus, Long sellerId);
}
