package com.sulek.order.batch.configuration;



import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Adam Hartley
 */
@Component
public class JobNotificationListener extends JobExecutionListenerSupport {


    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {

            JobInstance jobInstance = jobExecution.getJobInstance();



            for(StepExecution stepExecution : jobExecution.getStepExecutions()) {

            }
        } else {
        }
    }
}
