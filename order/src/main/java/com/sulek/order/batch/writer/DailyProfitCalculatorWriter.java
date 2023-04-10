package com.sulek.order.batch.writer;


import com.sulek.order.entity.DailyProfit;
import com.sulek.order.entity.Order;
import com.sulek.order.repository.DailyProfitRepository;
import com.sulek.order.repository.OrderRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class DailyProfitCalculatorWriter implements ItemWriter<List<DailyProfit>> {


    @Autowired
    private DailyProfitRepository dailyProfitRepository;

    @Override
    @Transactional
    public void write(List<? extends List<DailyProfit>> items) throws Exception {
        items.forEach(dailyProfitRepository::saveAll);
    }
}
