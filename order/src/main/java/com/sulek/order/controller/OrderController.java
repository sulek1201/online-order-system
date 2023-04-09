package com.sulek.order.controller;

import com.sulek.order.entity.User;
import com.sulek.order.model.OrderDto;
import com.sulek.order.model.ProductOrderDto;
import com.sulek.order.model.response.OrderResponseDto;
import com.sulek.order.security.JwtTokenUtil;
import com.sulek.order.service.OrderService;
import com.sulek.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/order")
@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    @Qualifier("OrderServiceImpl")
    private OrderService orderService;

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @RequestMapping(value = "/place-order", method = RequestMethod.POST)
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody ProductOrderDto productOrder, @RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(orderService.placeOrder(productOrder, getUser(jwtToken)));
    }

    @RequestMapping(value = "/check-order-by-status/{orderStatus}/{sellerId}", method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> checkOrderByStatus(@PathVariable("orderStatus") String orderStatus, @PathVariable("sellerId") Long sellerId) {
        return ResponseEntity.ok(orderService.checkOrderByStatus(orderStatus, sellerId));
    }

    private User getUser(String jwtToken) {
        String userName = JwtTokenUtil.parseUserNameFromJwt(jwtToken);
        return userService.findByUserName(userName);
    }
}
