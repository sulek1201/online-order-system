package com.sulek.order.repository;

import com.sulek.order.entity.DailyProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DailyProfitRepository extends JpaRepository<DailyProfit, Long> {
}
