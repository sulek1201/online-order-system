package com.sulek.order.service;

import com.sulek.order.enm.OrderStatus;
import com.sulek.order.entity.Order;
import com.sulek.order.entity.User;
import com.sulek.order.model.OrderDto;
import com.sulek.order.model.ProductOrderDto;
import com.sulek.order.model.response.OrderCheckResponseDto;
import com.sulek.order.model.response.OrderResponseDto;
import com.sulek.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("OrderServiceImpl")
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${product.seller.check.order.service}")
    private String checkOrderUrl;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(ProductOrderDto productOrder, User user) {
        OrderCheckResponseDto checkResponseDto = checkOrder(productOrder);
        if (!checkResponseDto.getCheckStatus()) {
            failOrder(productOrder, user, checkResponseDto.getSellerId());
            log.error("order checked and process is fail because: {}", checkResponseDto.getMsg());
            return OrderResponseDto.builder()
                    .orderStatus(OrderStatus.REJECTED.toString())
                    .msg(checkResponseDto.getMsg())
                    .build();
        }
        Order order = initiateOrder(productOrder, user, checkResponseDto.getSellerId());
        return OrderResponseDto.builder()
                .msg("order request is successfull")
                .orderStatus(order.getOrderStatus())
                .build();
    }

    @Override
    public List<OrderDto> checkOrderByStatus(String orderStatus, Long sellerId) {
        List<Order> orderList;
        if (orderStatus.equals(OrderStatus.ALL.toString())) {
            orderList = orderRepository.findAllBySellerId(sellerId);
        } else {
            orderList = orderRepository.findAllBySellerIdAndOrderStatus(sellerId, orderStatus);
        }
        if (orderList != null) {
            List<OrderDto> orderDtoList = new ArrayList<>();
            for (Order order : orderList) {
                OrderDto orderDto = OrderDto.builder()
                        .productId(order.getProductId())
                        .orderedUserAddress(order.getUserId().getAddress())
                        .orderedUserName(order.getUserId().getUsername())
                        .orderId(order.getId())
                        .quantity(order.getQuantity())
                        .orderStatus(order.getOrderStatus())
                        .build();
                orderDtoList.add(orderDto);
            }
            return orderDtoList;
        }
        return null;
    }

    private Order initiateOrder(ProductOrderDto productOrder, User user, Long sellerId) {
        Order order = Order.builder()
                .createdAt(new Date())
                .orderStatus(OrderStatus.CREATED.toString())
                .productId(productOrder.getProductId())
                .userId(user)
                .quantity(productOrder.getQuantity())
                .sellerId(sellerId)
                .build();
        orderRepository.save(order);
        log.info("order initiated: {}", order);
        return order;
    }

    private OrderCheckResponseDto checkOrder(ProductOrderDto productOrder) {
        OrderCheckResponseDto responseDto = restTemplate.postForObject(checkOrderUrl, productOrder, OrderCheckResponseDto.class);
        log.info("check order rest service response: {}", responseDto);
        return responseDto;
    }

    private void failOrder(ProductOrderDto productOrder, User user, Long sellerId) {
        Order order = Order.builder()
                .createdAt(new Date())
                .orderStatus(OrderStatus.REJECTED.toString())
                .productId(productOrder.getProductId())
                .userId(user)
                .quantity(productOrder.getQuantity())
                .sellerId(sellerId)
                .updatedAt(new Date())
                .build();
        orderRepository.save(order);
    }
}
