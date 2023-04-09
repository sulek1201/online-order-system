package com.sulek.order.service;

import com.sulek.order.model.request.OrderRequestDto;
import com.sulek.order.model.response.OrderResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponseDto placeOrder(OrderRequestDto orderRequesDto);
}
