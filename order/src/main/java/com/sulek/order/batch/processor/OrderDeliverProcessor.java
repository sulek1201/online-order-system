package com.sulek.order.batch.processor;

import com.sulek.order.enm.OrderStatus;
import com.sulek.order.entity.Order;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OrderDeliverProcessor implements ItemProcessor<List<Order>, List<Order>> {


    @Override
    public List<Order> process(List<Order> orderList) {
        for (Order order : orderList) {
            order.setOrderStatus(OrderStatus.DELIVERED.toString());
            order.setUpdatedAt(new Date());
        }
        return orderList;
    }
}
