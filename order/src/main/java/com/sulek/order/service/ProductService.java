package com.sulek.order.service;

import com.sulek.order.model.response.ProductListResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductListResponse> getAllProductList();

    List<ProductListResponse> getFilteredProductList(String name, String description);
}
