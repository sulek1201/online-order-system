package com.sulek.seller.service;


import com.sulek.seller.dto.*;
import com.sulek.seller.entity.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderResponseDto> checkOrderByStatus(String orderStatus,Seller seller);

     OrderCheckResponseDto checkOrder(ProductOrderDto productOrderDto);

    ApproveOrderResponse approveOrder(Long orderId, Seller seller);

    RejectOrderResponse rejectOrder(Long orderId, Long sellerId);
}
