package com.projjwal.batch.job.config;

import com.projjwal.batch.job.dto.VechileDTO;
import com.projjwal.batch.job.listner.CustomJobExecutionListner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ImportVechileInvoicesJob {

    private final CustomJobExecutionListner customJobExecutionListner;

    @Bean
    public Job importVeichleJob(JobRepository jobRepository, Step importVeichleStep)
    {
        return new JobBuilder("importVeichleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importVeichleStep)
                .listener(customJobExecutionListner)
                .build();
    }


    @Bean
    public Step importVeichleStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager)
    {
        return new StepBuilder("importVeichleStep", jobRepository)
                .<VechileDTO, VechileDTO>chunk(100, platformTransactionManager)
                .reader(vechileFlatFileItemReader())
                .processor(item -> {
                    log.info("Processing the items {}", item);
                    return item;
                })
                .writer(item -> {
                    log.info("Writing items {} ", item);;
                })
                .build();

    }

    public FlatFileItemReader<VechileDTO> vechileFlatFileItemReader()
    {
        return new FlatFileItemReaderBuilder<VechileDTO>()
                .resource(new ClassPathResource("/data/tesla_invoices.csv"))
                .name("Veichle Item reader")
                .saveState(Boolean.FALSE)
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("referenceNumber","model","type","customerFullName")
                .comments("#")
                .targetType(VechileDTO.class)
                .build();
    }

}
