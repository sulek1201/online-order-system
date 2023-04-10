package com.sulek.order.service;

import com.sulek.order.enm.OrderStatus;
import com.sulek.order.entity.Order;
import com.sulek.order.entity.User;
import com.sulek.order.exception.CustomerNotFoundException;
import com.sulek.order.model.OrderDto;
import com.sulek.order.model.ProductOrderDto;
import com.sulek.order.model.response.*;
import com.sulek.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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

    @Value("${get.product.by.id.service}")
    private String getProductByIdUrl;
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
        Order order = initiateOrder(productOrder, user, checkResponseDto.getSellerId(), checkResponseDto.getTotalPrice());
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

    @Override
    public ApproveOrderRepsonse approveOrder(Long orderId, Long sellerId) {
        Order order = getOrder(orderId, sellerId);
        if (order.getOrderStatus().equals(OrderStatus.CREATED.toString())) {
            order.setOrderStatus(OrderStatus.ACCEPTED.toString());
            order.setUpdatedAt(new Date());
            try {
                orderRepository.save(order);
                log.info("order is updated: {}", order);
                return ApproveOrderRepsonse.builder()
                        .checkStatus(true)
                        .msg("order successufly approved")
                        .productId(order.getProductId())
                        .quantity(order.getQuantity())
                        .build();
            } catch (Exception e) {
                return ApproveOrderRepsonse.builder()
                        .checkStatus(false)
                        .msg("order could not approve because: " + e.getMessage())
                        .build();
            }
        } else {
            return ApproveOrderRepsonse.builder()
                    .checkStatus(false)
                    .msg("To approve order, order status must be CREATED")
                    .build();
        }
    }

    @Override
    public RejectOrderResponse rejectOrder(Long orderId, Long sellerId) {
        Order order = getOrder(orderId, sellerId);
        if (order.getOrderStatus().equals(OrderStatus.CREATED.toString()) || order.getOrderStatus().equals(OrderStatus.ACCEPTED.toString())) {
            order.setOrderStatus(OrderStatus.REJECTED.toString());
            order.setUpdatedAt(new Date());
            try {
                orderRepository.save(order);
                log.info("order is updated: {}", order);
                return RejectOrderResponse.builder()
                        .checkStatus(true)
                        .msg("order successufly rejected")
                        .build();
            } catch (Exception e) {
                return RejectOrderResponse.builder()
                        .checkStatus(false)
                        .msg("order could not reject because: " + e.getMessage())
                        .build();
            }
        } else {
            return RejectOrderResponse.builder()
                    .checkStatus(false)
                    .msg("To reject order, order status must be CREATED")
                    .build();
        }
    }

    @Override
    public RejectOrderResponse cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findByIdAndUserId(orderId, user);
        if (order == null) {
            throw new CustomerNotFoundException("user has no order for id: " + orderId);
        }
        if (!order.getOrderStatus().equals(OrderStatus.ACCEPTED.toString())) {
            order.setOrderStatus(OrderStatus.CANCELLED.toString());
            order.setUpdatedAt(new Date());
            try {
                orderRepository.save(order);
                log.info("order is updated: {}", order);
                return RejectOrderResponse.builder()
                        .checkStatus(true)
                        .msg("order successufly canceld")
                        .build();
            } catch (Exception e) {
                return RejectOrderResponse.builder()
                        .checkStatus(false)
                        .msg("order could not cancel because: " + e.getMessage())
                        .build();
            }
        } else {
            return RejectOrderResponse.builder()
                    .checkStatus(false)
                    .msg("To cancel order, order status can not be ACCEPTED")
                    .build();
        }
    }

    @Override
    public List<OrderListResponse> getUserOrderList(User user) {
        List<Order> orderList = orderRepository.findAllByUserId(user);
        List<OrderListResponse> orderListResponseList = new ArrayList<>();
        for (Order order : orderList) {
            OrderListResponse orderListResponse = OrderListResponse.builder()
                    .orderedDate(order.getCreatedAt())
                    .orderStatus(order.getOrderStatus())
                    .build();
            String url = getProductByIdUrl.concat("/" + order.getProductId().toString());
            ProductListResponse productListResponse = restTemplate.getForObject(url, ProductListResponse.class);
            orderListResponse.setProductListResponse(productListResponse);
            orderListResponseList.add(orderListResponse);
        }
        return orderListResponseList;
    }

    private Order getOrder(Long orderId, Long sellerId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new CustomerNotFoundException("no order for id: " + orderId);
        }
        if (!order.getSellerId().equals(sellerId)) {
            throw new CustomerNotFoundException("Seller has not this order for id: " + orderId);
        }
        return order;
    }

    private Order initiateOrder(ProductOrderDto productOrder, User user, Long sellerId, BigDecimal totalPrice) {
        Order order = Order.builder()
                .createdAt(new Date())
                .orderStatus(OrderStatus.CREATED.toString())
                .productId(productOrder.getProductId())
                .userId(user)
                .quantity(productOrder.getQuantity())
                .sellerId(sellerId)
                .totalPrice(totalPrice)
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
