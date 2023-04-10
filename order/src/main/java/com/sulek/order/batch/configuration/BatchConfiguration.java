package com.sulek.order.batch.configuration;



import com.sulek.order.batch.processor.DailyProfitCalculatorProcessor;
import com.sulek.order.batch.processor.OrderDeliverProcessor;
import com.sulek.order.batch.reader.DailyProfitCalculatorReader;
import com.sulek.order.batch.reader.OrderDeliverReader;
import com.sulek.order.batch.writer.DailyProfitCalculatorWriter;
import com.sulek.order.batch.writer.OrderDeliverWriter;
import com.sulek.order.entity.DailyProfit;
import com.sulek.order.entity.Order;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Autowired
    private JobNotificationListener jobNotificationListener;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job orderDeliveredBatchJob() {
        return jobBuilderFactory.get("orderDeliveredBatchJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobNotificationListener)
                .flow(orderDeliveredBatchStep())
                .end()
                .build();
    }

    @Bean
    public Step orderDeliveredBatchStep() {
        return stepBuilderFactory.get("orderDeliveredBatchStep").<List<Order>, List<Order>>chunk(1)
                .reader(orderDeliveredReader())
                .processor(orderDeliverProcessor())
                .writer(orderDeliverWriter())
                .throttleLimit(1)
                .build();
    }


    @Bean
    public Job dailyProfitCalculatorBatchJob() {
        return jobBuilderFactory.get("dailyProfitCalculatorBatchJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobNotificationListener)
                .flow(dailyProfitCalculatorBatchStep())
                .end()
                .build();
    }

    @Bean
    public Step dailyProfitCalculatorBatchStep() {
        return stepBuilderFactory.get("dailyProfitCalculatorBatchStep").<List<Long>, List<DailyProfit>>chunk(1)
                .reader(dailyProfitCalculatorReader())
                .processor(dailyProfitCalculatorProcessor())
                .writer(dailyProfitCalculatorWriter())
                .throttleLimit(1)
                .build();
    }

    @Bean
    public ItemReader<List<Order>> orderDeliveredReader() {
        return new OrderDeliverReader();
    }

    @Bean
    public ItemProcessor<List<Order>, List<Order>> orderDeliverProcessor() {
        return new OrderDeliverProcessor();
    }

    @Bean
    public ItemWriter<List<Order>> orderDeliverWriter() {
        return new OrderDeliverWriter();
    }

    @Bean
    public ItemReader<List<Long>> dailyProfitCalculatorReader() {
        return new DailyProfitCalculatorReader();
    }

    @Bean
    public ItemProcessor<List<Long>, List<DailyProfit>> dailyProfitCalculatorProcessor() {
        return new DailyProfitCalculatorProcessor();
    }

    @Bean
    public ItemWriter<List<DailyProfit>> dailyProfitCalculatorWriter() {
        return new DailyProfitCalculatorWriter();
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }
}
