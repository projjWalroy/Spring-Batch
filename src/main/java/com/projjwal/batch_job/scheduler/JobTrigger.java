package com.projjwal.batch_job.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobTrigger {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Scheduled(cron = "0/30 * * ? * *")
    void setJobLauncher() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        log.info("=========> launching the Job");

        var jobParameter = new JobParametersBuilder();
        jobParameter.addDate("unique",new Date());

        JobExecution jobExecution = this.jobLauncher.run(job, jobParameter.toJobParameters());

        log.info("Job finished with status {} ", jobExecution.getExitStatus());
    }
}
