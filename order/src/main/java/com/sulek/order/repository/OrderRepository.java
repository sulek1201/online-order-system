package com.sulek.order.repository;

import com.sulek.order.entity.Order;
import com.sulek.order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllBySellerIdAndOrderStatus(Long sellerId, String orderStatus);

    List<Order> findAllBySellerId(Long sellerId);

    List<Order> findAllByUserId(User user);

    Order findByIdAndUserId(Long sellerId, User user);

    List<Order> findByOrderStatusAndUpdatedAtBefore(String orderStatus, Date updatedDate);

    @Query("SELECT DISTINCT o.sellerId FROM Order o WHERE o.createdAt >= :startOfDay AND o.createdAt < :endOfDay AND o.orderStatus = 'DELIVERED' AND o.isCalculated = false")
    List<Long> findDistinctSellerIds(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);


    @Query("SELECT o FROM Order o WHERE o.sellerId = :sellerId AND o.createdAt >= :startOfDay AND o.createdAt < :endOfDay AND o.isCalculated = false AND o.orderStatus = 'DELIVERED'")
    List<Order> findBySellerIdAndOrderStatusAndUpdatedAtBetween(@Param("sellerId") Long sellerId, @Param("startOfDay") Date startDate, @Param("endOfDay") Date endDate);

}
