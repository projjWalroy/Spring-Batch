package com.projjwal.batch.job.listner;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomJobExecutionListner implements JobExecutionListener{

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getStepExecutions()
                .stream()
                .findFirst()
                .ifPresent(stepExecution -> {
                    long writeCount = stepExecution.getWriteCount();
                    log.info(String.format("The job has written %s lines", writeCount));
                });
    }
}
