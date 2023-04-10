package com.sulek.order.batch.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
@EnableBatchProcessing
public class ScheduleJob {

    private final static Logger log = LogManager.getLogger(ScheduleJob.class);

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "0 0/15 * * * *")
    public void runOrderDeliverBatch() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        try {
            Job job = jobRegistry.getJob("orderDeliveredBatchJob");
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.info("error: {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 22 * * *")  // 22:00'da çalışacak şekilde ayarlandı, denemek için cron value 0 0/1 * * * * şeklinde değiştirilebilir
    public void runDailyProfitCalculatorBatch() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        try {
            Job job = jobRegistry.getJob("dailyProfitCalculatorBatchJob");
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.info("error: {}", e.getMessage());
        }
    }
}
