package com.sulek.seller.service;

import com.sulek.seller.dto.*;
import com.sulek.seller.entity.Product;
import com.sulek.seller.entity.Seller;
import com.sulek.seller.exception.SellerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component("OrderServiceImpl")
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("ProductServiceImpl")
    private ProductService productService;

    @Value("${check.created.order.service}")
    private String checkOrderUrl;

    @Value("${approve.order.service}")
    private String approveOrderUrl;

    @Value("${reject.order.service}")
    private String rejectOrderUrl;

    @Override
    public List<OrderResponseDto> checkOrderByStatus(String orderStatus, Seller seller) {
        String url = checkOrderUrl.concat("/" + orderStatus.concat("/" + seller.getId().toString()));
        ArrayList<OrderResponseDto> orderResponseDtoList = restTemplate.getForObject(url, ArrayList.class);
        log.info("check order rest service response: {}", orderResponseDtoList);
        return orderResponseDtoList;
    }

    @Override
    public OrderCheckResponseDto checkOrder(ProductOrderDto productOrderDto) {
        Product product = productService.getProductById(productOrderDto.getProductId());
        if (productOrderDto.getQuantity().compareTo(product.getQuantity()) == 1) {
            return OrderCheckResponseDto.builder()
                    .checkStatus(false)
                    .sellerId(product.getSellerId().getId())
                    .msg("Requested quantity can not bigger than current quantity")
                    .build();
        }

        return OrderCheckResponseDto.builder()
                .checkStatus(true)
                .sellerId(product.getSellerId().getId())
                .msg("Request successfull")
                .build();
    }

    @Override
    public ApproveOrderResponse approveOrder(Long orderId, Seller seller) {
        String url = approveOrderUrl.concat("/" + orderId.toString().concat("/" + seller.getId().toString()));
        ApproveOrderResponse approveOrderResponse = restTemplate.postForObject(url, null, ApproveOrderResponse.class);
        if (approveOrderResponse == null) {
            throw new SellerNotFoundException("approve service response can not be null");
        }
        log.info("approve order rest service response: {}", approveOrderResponse);
        Boolean productHandled = productService.handleProduct(approveOrderResponse.getProductId(), approveOrderResponse.getQuantity());
        if (productHandled) {
            return approveOrderResponse;
        } else {
            rejectOrder(orderId, seller.getId());
            return ApproveOrderResponse.builder()
                    .checkStatus(false)
                    .msg("order rejected pls check current quantity of productID: " + approveOrderResponse.getProductId())
                    .build();
        }
    }

    @Override
    public RejectOrderResponse rejectOrder(Long orderId, Long sellerId) {
        String url = rejectOrderUrl.concat("/" + orderId.toString().concat("/" + sellerId.toString()));
        return restTemplate.postForObject(url, null, RejectOrderResponse.class);
    }
}
