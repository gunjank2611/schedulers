package com.thermax.cp.salesforce.controller;

import com.thermax.cp.salesforce.feign.request.FileUploadFeignClient;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.service.BatchDataServiceImpl;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/sfdc/batch")
public class SfdcBatchUpdateController {

    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;

    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;

    @Autowired
    private FileUploadFeignClient fileUploadFeignClient;

    @Autowired
    private BatchDataServiceImpl batchDataService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job productsJob;

    @Autowired
    Job sparesJob;

    @Autowired
    Job accountsJob;

    @Autowired
    Job opportunitiesJob;

    @Autowired
    Job servicesJob;

    @Autowired
    Job recommendationsJob;

    @Autowired
    Job assetsJob;

    @Autowired
    Job pricebooksJob;

    @Autowired
    Job pricebookEntriesJob;

    @Autowired
    Job usersJob;

    @Autowired
    Job complaintsJob;

    @Autowired
    Job ordersJob;

    @Autowired
    Job orderItemsJob;

    @Autowired
    Job opportunityLineItemsJob;

    @Autowired
    Job proposalsJob;

    @Autowired
    Job orderStatusJob;

    @Autowired
    Job eligibleSpareServicesJob;

    @Autowired
    Job assetHistoryJob;

    Map<String, JobParameter> maps = new HashMap<>();

    JobParameters parameters = new JobParameters(maps);

    @Autowired
    JobExplorer jobExplorer;


    @GetMapping("/loadProducts/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Products loaded successfully")
    public void loadProducts(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(productsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", productsJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(productsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadOrderStatus")
    @ResponseStatus(value = HttpStatus.OK, reason = "OrderStatus loaded successfully")
    public void loadOrderStatus(@RequestParam String url) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(orderStatusJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNextStatus(url);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", orderStatusJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(orderStatusJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
    }


    @GetMapping("/loadAccounts/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Accounts loaded successfully")
    public void loadAccounts(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(accountsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", accountsJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(accountsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadRecommendations/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Recommendations loaded successfully")
    public void loadRecommendations(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(recommendationsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", recommendationsJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(recommendationsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadServices/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Spares loaded successfully")
    public void loadServices(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(servicesJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", servicesJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(servicesJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadSpares/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Spares loaded successfully")
    public void loadSpares(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(sparesJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", sparesJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(sparesJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadAssets/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Assets loaded successfully")
    public void loadAssets(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(assetsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", assetsJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(assetsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadOpportunities/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Opportunities loaded successfully")
    public void loadOpportunities(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(opportunitiesJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", opportunitiesJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(opportunitiesJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadPricebooks/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "pricebooks loaded successfully")
    public void loadPriceBooks(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(pricebooksJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", pricebooksJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(pricebooksJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadPricebookEntries/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Pricebook entries loaded successfully")
    public void loadPricebookEntries(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(pricebookEntriesJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", pricebookEntriesJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(pricebookEntriesJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadUsers/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Users loaded successfully")
    public void loadUsers(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(usersJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", usersJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(usersJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadComplaints/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "complaints loaded successfully")
    public void loadComplaints(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(complaintsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", complaintsJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(complaintsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadOrders/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "orders loaded successfully")
    public void loadOrders(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(ordersJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", ordersJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(ordersJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }


    @GetMapping("/loadOrderItems/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "order items loaded successfully")
    public void loadOrderItems(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(orderItemsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);

            log.info("Trying to restart task \"{}\" with the parameters [{}]", orderItemsJob, parameters);
        }

        JobExecution jobExecution = jobLauncher.run(orderItemsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadOpportunityLineItems/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "order items loaded successfully")
    public void loadOpportunityLineItems(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(opportunityLineItemsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", opportunityLineItemsJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(opportunityLineItemsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadProposals/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "proposals loaded successfully")
    public void loadProposals(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(proposalsJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", proposalsJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(proposalsJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadEligibleSparesServices/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "proposals loaded successfully")
    public void loadEligibleSparesServices(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(eligibleSpareServicesJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", eligibleSpareServicesJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(eligibleSpareServicesJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }

    @GetMapping("/loadAssetHistory/{frequency}")
    @ResponseStatus(value = HttpStatus.OK, reason = "asset history loaded successfully")
    public void loadAssetHistory(@PathVariable String frequency) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobInstance existingInstance = jobExplorer.getLastJobInstance(assetHistoryJob.getName());
        if (existingInstance!=null)
        {
            parameters = getNext(frequency);
            log.info("Trying to restart task \"{}\" with the parameters [{}]", assetHistoryJob, parameters);
        }
        JobExecution jobExecution = jobLauncher.run(assetHistoryJob, parameters);
        log.info("JobExecution  {} " , jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

    }
    public JobParameters getNext(String frequency) {
        long id = new Date().getTime();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("run.id", id)
                .addString("frequency", frequency).toJobParameters();
        return jobParameters;
    }
    public JobParameters getNextStatus(String url) {
        long id = new Date().getTime();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("url", url).toJobParameters();
        return jobParameters;
    }



}
