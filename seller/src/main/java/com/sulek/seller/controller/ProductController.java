package com.sulek.seller.controller;


import com.sulek.seller.dto.ProductListResponse;
import com.sulek.seller.dto.ProductRequestDto;
import com.sulek.seller.dto.ProductResponseDto;
import com.sulek.seller.entity.Seller;
import com.sulek.seller.security.JwtTokenUtil;
import com.sulek.seller.service.OrderServiceImpl;
import com.sulek.seller.service.ProductService;
import com.sulek.seller.service.SellerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/product")
@RestController
@CrossOrigin
public class ProductController {

    private final static Logger log = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    @Qualifier(value = "ProductServiceImpl")
    private ProductService productService;

    @Autowired
    @Qualifier(value = "SellerServiceImpl")
    private SellerService sellerService;


    @RequestMapping(value = "/add-product", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addProduct(@RequestBody ProductRequestDto productRequestDto, @RequestHeader("Authorization") String jwtToken) {

        Boolean response = productService.addProduct(productRequestDto, getSeller(jwtToken));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/update-food/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDto productRequestDto, @RequestHeader("Authorization") String jwtToken) {
        log.info("updateFood service running with: {}", productRequestDto);
        Boolean response = productService.updateProduct(id, productRequestDto, getSeller(jwtToken));
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/delete-food/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwtToken) {
        log.info("deleteFood service running with id: {}", id);
        Boolean response = productService.deleteProduct(id, getSeller(jwtToken));
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/get-all-products-by-seller", method = RequestMethod.GET)
    public ResponseEntity<List<ProductResponseDto>> getAllProductBySeller(@RequestHeader("Authorization") String jwtToken) {
        Seller seller = getSeller(jwtToken);
        log.info("getAllProductBySeller service running with for seller: {}", seller.getId());
        return ResponseEntity.ok().body(productService.getAllProductBySeller(seller));
    }

    @RequestMapping(value = "/all-product-list", method = RequestMethod.GET)
    public ResponseEntity<List<ProductListResponse>> getAllProductList() {
        return ResponseEntity.ok(productService.getAllProductList());
    }

    @RequestMapping(value = "/filtered-product-list/{name}/{description}", method = RequestMethod.GET)
    public ResponseEntity<List<ProductListResponse>> getFilteredProductList(@PathVariable("name") String name, @PathVariable("description") String description) {
        return ResponseEntity.ok(productService.getFilteredProductList(name, description));
    }

    @RequestMapping(value = "/get-product-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductListResponse> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductResponseById(id));
    }

    private Seller getSeller(String jwtToken) {
        String userName = JwtTokenUtil.parseUserNameFromJwt(jwtToken);
        return sellerService.findByUserName(userName);
    }
}
