package com.sulek.order.service;

import com.sulek.order.entity.User;
import com.sulek.order.model.OrderDto;
import com.sulek.order.model.ProductOrderDto;
import com.sulek.order.model.response.ApproveOrderRepsonse;
import com.sulek.order.model.response.OrderListResponse;
import com.sulek.order.model.response.OrderResponseDto;
import com.sulek.order.model.response.RejectOrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponseDto placeOrder(ProductOrderDto productOrder, User user);

    List<OrderDto> checkOrderByStatus(String orderStatus, Long sellerId);

    ApproveOrderRepsonse approveOrder(Long orderId, Long sellerId);

    RejectOrderResponse rejectOrder(Long orderId, Long sellerId);

    RejectOrderResponse cancelOrder(Long orderId, User user);

    List<OrderListResponse> getUserOrderList(User user);
}
