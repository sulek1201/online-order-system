package com.sulek.seller.service;


import com.sulek.seller.dto.ProductRequestDto;
import com.sulek.seller.dto.ProductResponseDto;
import com.sulek.seller.entity.Product;
import com.sulek.seller.entity.Seller;
import com.sulek.seller.exception.SellerNotFoundException;
import com.sulek.seller.exception.DataSaveException;
import com.sulek.seller.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component(value = "ProductServiceImpl")
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Boolean addProduct(ProductRequestDto productRequestDto, Seller seller) {
        Product product = Product.builder()
                .productName(productRequestDto.getProductName())
                .description(productRequestDto.getDescription())
                .quantity(productRequestDto.getQuantity())
                .sellerId(seller)
                .build();
        product.setCreatedAt(new Date());
        product.setStatus(true);
        try {
            productRepository.save(product);
            log.info("product saved: {}", product);
            return true;
        } catch (Exception e) {
            throw new DataSaveException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean updateProduct(Long id, ProductRequestDto productRequestDto, Seller seller) {
        Product product = getProductByIdAndSeller(productRepository, id, seller);
        if (!productRequestDto.getProductName().isEmpty()) {
            product.setProductName(productRequestDto.getProductName());
        }
        if (!productRequestDto.getDescription().isEmpty()) {
            product.setDescription(productRequestDto.getDescription());
        }
        if (productRequestDto.getQuantity() != null) {
            product.setQuantity(productRequestDto.getQuantity());
        }
        try {
            productRepository.save(product);
            log.info("product updated: {}", product);
            return true;
        } catch (Exception e) {
            throw new DataSaveException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteProduct(Long id, Seller seller) {
        Product product = getProductByIdAndSeller(productRepository, id, seller);
        try {
            productRepository.delete(product);
            log.info("{} id product deleted", id);
            return true;
        } catch (Exception e) {
            log.error("{} id product could not delete because {}", id, e.getMessage());
            throw new DataSaveException(e.getMessage());
        }
    }

    @Override
    public List<ProductResponseDto> getAllProductBySeller(Seller seller) {
        List<Product> productList = productRepository.findAllBySellerId(seller);
        if (productList != null) {
            List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
            for (Product product : productList) {
                ProductResponseDto productResponseDto = ProductResponseDto.builder()
                        .name(product.getProductName())
                        .description(product.getDescription())
                        .quantity(product.getQuantity())
                        .build();
                productResponseDtoList.add(productResponseDto);
            }
            return productResponseDtoList;
        }
        return null;
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new SellerNotFoundException("no product for id: " + productId);
        }
        return product;
    }

    private Product getProductByIdAndSeller(ProductRepository productRepository, Long id, Seller seller) {
        Product product = productRepository.findByIdAndSellerId(id, seller);
        if (product == null) {
            log.info("Seller has no product for given id: {}", id);
            throw new SellerNotFoundException("Seller has no product for given id: " + id);
        }
        return product;
    }
}
