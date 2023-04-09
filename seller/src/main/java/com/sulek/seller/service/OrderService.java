package com.sulek.seller.service;


import com.sulek.seller.dto.OrderCheckResponseDto;
import com.sulek.seller.dto.OrderResponseDto;
import com.sulek.seller.dto.ProductOrderDto;
import com.sulek.seller.entity.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderResponseDto> checkOrderByStatus(String orderStatus,Seller seller);

     OrderCheckResponseDto checkOrder(ProductOrderDto productOrderDto);
}
