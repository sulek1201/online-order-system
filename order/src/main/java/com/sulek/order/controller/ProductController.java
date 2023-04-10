package com.sulek.order.controller;

import com.sulek.order.entity.User;
import com.sulek.order.model.OrderDto;
import com.sulek.order.model.ProductOrderDto;
import com.sulek.order.model.request.ProductListRequest;
import com.sulek.order.model.response.ApproveOrderRepsonse;
import com.sulek.order.model.response.OrderResponseDto;
import com.sulek.order.model.response.ProductListResponse;
import com.sulek.order.model.response.RejectOrderResponse;
import com.sulek.order.security.JwtTokenUtil;
import com.sulek.order.service.OrderService;
import com.sulek.order.service.ProductService;
import com.sulek.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/product")
@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    @Qualifier("ProductServiceImpl")
    private ProductService productService;

    @RequestMapping(value = "/all-product-list", method = RequestMethod.POST)
    public ResponseEntity<List<ProductListResponse>> getAllProductList() {
        return ResponseEntity.ok(productService.getAllProductList());
    }

    @RequestMapping(value = "/filtered-product-list", method = RequestMethod.POST)
    public ResponseEntity<List<ProductListResponse>> getFilteredProductList(@RequestBody ProductListRequest productListRequest) {
        return ResponseEntity.ok(productService.getFilteredProductList(productListRequest.getName(), productListRequest.getDescription()));
    }

}
