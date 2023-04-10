package com.sulek.order.service;

import com.sulek.order.model.response.OrderCheckResponseDto;
import com.sulek.order.model.response.ProductListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component("ProductServiceImpl")
@Slf4j
public class ProductServiceImpl implements ProductService{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${all.product.list.service}")
    private String getAllProductListUrl;

    @Value("${filtered.product.list.service}")
    private String getFilteredListUrl;

    @Override
    public List<ProductListResponse> getAllProductList() {
        ArrayList<ProductListResponse> responseDto = restTemplate.getForObject(getAllProductListUrl, ArrayList.class);
        log.info("getAllProductList rest service response: {}", responseDto);
        return responseDto;
    }

    @Override
    public List<ProductListResponse> getFilteredProductList(String name, String description) {
        String url = getFilteredListUrl.concat("/" + name.concat("/" + description));
        ArrayList<ProductListResponse> responseDto = restTemplate.getForObject(url, ArrayList.class);
        log.info("getFilteredProductList rest service response: {}", responseDto);
        return responseDto;
    }
}
