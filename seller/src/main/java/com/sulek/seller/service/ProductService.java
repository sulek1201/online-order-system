package com.sulek.seller.service;


import com.sulek.seller.dto.ProductRequestDto;
import com.sulek.seller.dto.ProductResponseDto;
import com.sulek.seller.entity.Product;
import com.sulek.seller.entity.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Boolean addProduct(ProductRequestDto productRequestDto, Seller seller);

    Boolean updateProduct(Long id, ProductRequestDto productRequestDto, Seller seller);

    Boolean deleteProduct(Long productId, Seller seller);

    List<ProductResponseDto> getAllProductBySeller(Seller seller);

    Product getProductById(Long productId);

}
