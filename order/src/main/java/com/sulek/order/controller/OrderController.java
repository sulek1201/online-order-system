package com.sulek.order.controller;

import com.sulek.order.model.request.OrderRequestDto;
import com.sulek.order.model.response.OrderResponseDto;
import com.sulek.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order")
@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    @Qualifier("OrderServiceImpl")
    private OrderService orderService;


    @RequestMapping(value = "/place-order", method = RequestMethod.POST)
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequestDto));
    }
}
