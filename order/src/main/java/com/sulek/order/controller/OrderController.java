package com.sulek.order.controller;

import com.sulek.order.entity.User;
import com.sulek.order.model.OrderDto;
import com.sulek.order.model.ProductOrderDto;
import com.sulek.order.model.response.ApproveOrderRepsonse;
import com.sulek.order.model.response.OrderListResponse;
import com.sulek.order.model.response.OrderResponseDto;
import com.sulek.order.model.response.RejectOrderResponse;
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

    @RequestMapping(value = "/approve-order/{orderId}/{sellerId}", method = RequestMethod.POST)
    public ResponseEntity<ApproveOrderRepsonse> approveOrder(@PathVariable("orderId") Long orderId, @PathVariable("sellerId") Long sellerId) {
        return ResponseEntity.ok(orderService.approveOrder(orderId, sellerId));
    }

    @RequestMapping(value = "/reject-order/{orderId}/{sellerId}", method = RequestMethod.POST)
    public ResponseEntity<RejectOrderResponse> rejectOrder(@PathVariable("orderId") Long orderId, @PathVariable("sellerId") Long sellerId) {
        return ResponseEntity.ok(orderService.rejectOrder(orderId, sellerId));
    }

    @RequestMapping(value = "/cancel-order/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<RejectOrderResponse> cancelOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, getUser(jwtToken)));
    }

    @RequestMapping(value = "/user-order-list", method = RequestMethod.GET)
    public ResponseEntity<List<OrderListResponse>> getUserOrderList(@RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(orderService.getUserOrderList(getUser(jwtToken)));
    }

    private User getUser(String jwtToken) {
        String userName = JwtTokenUtil.parseUserNameFromJwt(jwtToken);
        return userService.findByUserName(userName);
    }
}
