package com.sulek.order.repository;

import com.sulek.order.entity.Order;
import com.sulek.order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllBySellerIdAndOrderStatus(Long sellerId, String orderStatus);

    List<Order> findAllBySellerId(Long sellerId);

    List<Order> findAllByUserId(User user);

    Order findByIdAndUserId(Long sellerId, User user);

}
