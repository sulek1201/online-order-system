package com.sulek.seller.repository;


import com.sulek.seller.entity.Product;
import com.sulek.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByIdAndSellerId(Long id, Seller seller);

    List<Product> findAllBySellerId(Seller seller);

    List<Product> findAllByQuantityGreaterThan(BigDecimal quantity);

    List<Product> findAllByProductNameContainingOrDescriptionContainingAndQuantityGreaterThanEqual(String name, String description, BigDecimal quantity);

}
