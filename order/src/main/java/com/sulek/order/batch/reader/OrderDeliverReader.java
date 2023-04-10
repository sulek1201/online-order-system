package com.sulek.order.batch.reader;


import com.sulek.order.enm.OrderStatus;
import com.sulek.order.entity.Order;
import com.sulek.order.repository.OrderRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OrderDeliverReader implements ItemReader<List<Order>> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> read() {
        List<Order> orderList = orderRepository.findByOrderStatusAndUpdatedAtBefore(OrderStatus.ACCEPTED.toString(), new Date(System.currentTimeMillis() - 120000));
        if(orderList.size() == 0){
            return null;
        }
       return orderList;
    }
}
