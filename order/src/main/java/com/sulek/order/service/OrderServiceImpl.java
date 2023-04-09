package com.sulek.order.service;

import com.sulek.order.enm.OrderStatus;
import com.sulek.order.entity.Order;
import com.sulek.order.exception.DuplicateKeyValueException;
import com.sulek.order.model.request.CheckOrderRequestDto;
import com.sulek.order.model.request.OrderRequestDto;
import com.sulek.order.model.response.OrderCheckResponseDto;
import com.sulek.order.model.response.OrderResponseDto;
import com.sulek.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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
    public OrderResponseDto placeOrder(OrderRequestDto orderRequesDto) {
        Order order = initiateOrder();
        log.info("order initiated: {}", order);
        OrderCheckResponseDto checkResponseDto = checkOrder(orderRequesDto.getCheckOrderRequestDto());
        if (!checkResponseDto.getCheckStatus()) {
            failOrder(order);
            log.error("order checked and process is fail because: {}", checkResponseDto.getMsg());
            return OrderResponseDto.builder()
                    .orderStatus(false)
                    .msg(checkResponseDto.getMsg())
                    .build();
        }
        return OrderResponseDto.builder()
                .msg("order request is successfull")
                .orderStatus(true)
                .build();
    }

    private Order initiateOrder() {
        Order order = Order.builder()
                .createdAt(new Date())
                .orderStatus(OrderStatus.CREATED.toString())
                .build();
        return orderRepository.save(order);
    }

    private OrderCheckResponseDto checkOrder(CheckOrderRequestDto checkOrderRequestDto) {
        OrderCheckResponseDto responseDto = restTemplate.postForObject(checkOrderUrl, checkOrderRequestDto, OrderCheckResponseDto.class);
        log.info("check order rest service response: {}", responseDto);
        return responseDto;
    }

    private void failOrder(Order order) {
        order.setUpdatedAt(new Date());
        order.setOrderStatus(OrderStatus.REJECTED.toString());
        orderRepository.save(order);
    }
}
