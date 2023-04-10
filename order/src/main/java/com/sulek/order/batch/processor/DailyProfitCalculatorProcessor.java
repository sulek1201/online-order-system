package com.sulek.order.batch.processor;

import com.sulek.order.enm.OrderStatus;
import com.sulek.order.entity.DailyProfit;
import com.sulek.order.entity.Order;
import com.sulek.order.repository.OrderRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DailyProfitCalculatorProcessor implements ItemProcessor<List<Long>, List<DailyProfit>> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<DailyProfit> process(List<Long> sellerList) {
        List<DailyProfit> dailyProfitList = new ArrayList<>();
        List<Order> totalOrderList = new ArrayList<>();
        for (Long sellerId : sellerList) {
            DailyProfit dailyProfit = new DailyProfit();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date startOfDay = DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);
            Date endOfDay = DateUtils.addDays(startOfDay, 1);
            List<Order> orderList = orderRepository.findBySellerIdAndOrderStatusAndUpdatedAtBetween(sellerId, startOfDay, endOfDay);
            BigDecimal totalPrice = orderList.stream().map(Order::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            dailyProfit.setTotalProfit(totalPrice);
            dailyProfit.setSellerId(sellerId);
            dailyProfit.setCreatedAt(new Date());
            dailyProfitList.add(dailyProfit);
            totalOrderList.addAll(orderList);
        }
        totalOrderList.forEach(order -> order.setIsCalculated(true));
        totalOrderList.forEach(order -> order.setUpdatedAt(new Date()));
        orderRepository.saveAll(totalOrderList);
        return dailyProfitList;
    }
}
