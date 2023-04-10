package com.sulek.order.batch.reader;



import com.sulek.order.repository.OrderRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DailyProfitCalculatorReader implements ItemReader<List<Long>> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Long> read() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date startOfDay = DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);
        Date endOfDay = DateUtils.addDays(startOfDay, 1);
        List<Long> sellerIds = orderRepository.findDistinctSellerIds(startOfDay, endOfDay);
        if(sellerIds.size() == 0){
            return null;
        }
       return sellerIds;
    }
}
