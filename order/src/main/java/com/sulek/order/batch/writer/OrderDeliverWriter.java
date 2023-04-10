package com.sulek.order.batch.writer;


import com.sulek.order.entity.Order;
import com.sulek.order.repository.OrderRepository;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class OrderDeliverWriter implements ItemWriter<List<Order>> {


    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public void write(List<? extends List<Order>> items) throws Exception {
        items.forEach(orderRepository::saveAll);
    }
}
