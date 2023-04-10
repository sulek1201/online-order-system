package com.sulek.seller.controller;

import com.sulek.seller.dto.*;
import com.sulek.seller.entity.Seller;
import com.sulek.seller.security.JwtTokenUtil;
import com.sulek.seller.service.OrderService;
import com.sulek.seller.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/order")
@RestController
@CrossOrigin
@Slf4j
public class OrderController {

    @Autowired
    @Qualifier("OrderServiceImpl")
    private OrderService orderService;

    @Autowired
    @Qualifier(value = "SellerServiceImpl")
    private SellerService sellerService;

    @RequestMapping(value = "/check-order", method = RequestMethod.POST)
    public OrderCheckResponseDto checkOrder(@RequestBody ProductOrderDto productOrderDto) {
        log.info("check order service running with: {}", productOrderDto);
        return orderService.checkOrder(productOrderDto);
    }

    @RequestMapping(value = "/check-order-by-status/{orderStatus}", method = RequestMethod.GET)
    public ResponseEntity<List<OrderResponseDto>> checkOrderByStatus(@PathVariable("orderStatus") String orderStatus, @RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(orderService.checkOrderByStatus(orderStatus, getSeller(jwtToken)));
    }

    @RequestMapping(value = "/approve-order/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<ApproveOrderResponse> approveOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(orderService.approveOrder(orderId, getSeller(jwtToken)));
    }

    @RequestMapping(value = "/reject-order/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<RejectOrderResponse> rejectOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(orderService.rejectOrder(orderId, getSeller(jwtToken).getId()));
    }

    private Seller getSeller(String jwtToken) {
        String userName = JwtTokenUtil.parseUserNameFromJwt(jwtToken);
        return sellerService.findByUserName(userName);
    }
}