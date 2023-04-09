package com.sulek.seller.service;

import com.sulek.seller.dto.OrderCheckResponseDto;
import com.sulek.seller.dto.OrderResponseDto;
import com.sulek.seller.dto.ProductOrderDto;
import com.sulek.seller.entity.Product;
import com.sulek.seller.entity.Seller;
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
}
