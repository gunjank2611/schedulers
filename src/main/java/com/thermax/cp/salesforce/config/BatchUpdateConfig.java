package com.thermax.cp.salesforce.config;

import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderItemsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import com.thermax.cp.salesforce.dto.users.SFDCUserDTOList;
import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.itemprocessor.AccountsProcessor;
import com.thermax.cp.salesforce.itemprocessor.ProductItemProcessor;
import com.thermax.cp.salesforce.itemprocessor.RecommendationsProcessor;
import com.thermax.cp.salesforce.itemreader.*;
import com.thermax.cp.salesforce.itemwriter.*;
import com.thermax.cp.salesforce.utils.CSVWrite;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchUpdateConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CSVWrite csvWrite;

    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;


    public JobParametersIncrementer jobParametersIncrementer() {
        return new RunIdIncrementer();
    }

    public JobBuilder getJobBuilder(String jobName) {
        return jobBuilderFactory.get(jobName)
                .incrementer(jobParametersIncrementer());
    }

        @Bean
    public Step loadProducts(
                      ) {
        return stepBuilderFactory.get("load-products")
                .<SFDCProductInfoDTO, SFDCProductInfoDTO>chunk(100)
                .reader(productItemReader(sfdcBatchDataDetailsRequest))
                .processor(new ProductItemProcessor())
                .writer(new ProductsDBWriter())
                .build();
    }
    @Bean
    public Step loadAccounts(
    ) {
        return stepBuilderFactory.get("load-accounts")
                .<SFDCAccountInfoDTO, SFDCAccountInfoDTO>chunk(100)
                .reader(accountsItemReader(sfdcBatchDataDetailsRequest))
                .processor(new AccountsProcessor())
                .writer(new AccountsDBWriter())
                .build();
    }

    @Bean
    public Step loadRecommendations(
    ) {
        return stepBuilderFactory.get("load-recommendations")
                .<SFDCRecommendationsDTO, SFDCRecommendationsDTO>chunk(100)
                .reader(recommendationsReader(sfdcBatchDataDetailsRequest))
                .processor(new RecommendationsProcessor())
                .writer(new RecommendationsWriter(csvWrite))
                .build();
    }

    @Bean
    public Step loadServices(
    ) {
        return stepBuilderFactory.get("load-services")
                .<SFDCServicesDTO, SFDCServicesDTO>chunk(100)
                .reader(servicesReader(sfdcBatchDataDetailsRequest))
                .writer(new ServicesWriter())
                .build();
    }

    @Bean
    public Step loadSpares(
    ) {
        return stepBuilderFactory.get("load-spares")
                .<SFDCSparesDTO, SFDCSparesDTO>chunk(100)
                .reader(sparesReader(sfdcBatchDataDetailsRequest))
                .writer(new SparesWriter())
                .build();
    }

    @Bean
    public Step loadAssets(
    ) {
        return stepBuilderFactory.get("load-assets")
                .<SFDCAssetDTO, SFDCAssetDTO>chunk(100)
                .reader(assetsReader(sfdcBatchDataDetailsRequest))
                .writer(new AssetWriter())
                .build();
    }

    @Bean
    public Step loadOpportunities(
    ) {
        return stepBuilderFactory.get("load-opportunities")
                .<SFDCOpportunityDTO, SFDCOpportunityDTO>chunk(100)
                .reader(opportunityReader(sfdcBatchDataDetailsRequest))
                .writer(new OpportunityWriter())
                .build();
    }

    @Bean
    public Step loadPricebooks(
    ) {
        return stepBuilderFactory.get("load-pricebooks")
                .<SFDCPricebookDTO, SFDCPricebookDTO>chunk(100)
                .reader(pricebookReader(sfdcBatchDataDetailsRequest))
                .writer(new PricebookWriter())
                .build();
    }

    @Bean
    public Step loadPricebookEntries(
    ) {
        return stepBuilderFactory.get("load-pricebookentries")
                .<SFDCPricebookEntryDTO, SFDCPricebookEntryDTO>chunk(100)
                .reader(pricebookEntryReader(sfdcBatchDataDetailsRequest))
                .writer(new PricebookEntryWriter())
                .build();
    }

    @Bean
    public Step loadUsers(
    ) {
        return stepBuilderFactory.get("load-users")
                .<SFDCUsersDTO, SFDCUsersDTO>chunk(100)
                .reader(usersReader(sfdcBatchDataDetailsRequest))
                .writer(new UsersWriter())
                .build();
    }

    @Bean
    public Step loadComplaints(
    ) {
        return stepBuilderFactory.get("load-complaints")
                .<SFDCComplaintsDTO, SFDCComplaintsDTO>chunk(100)
                .reader(complaintsReader(sfdcBatchDataDetailsRequest))
                .writer(new ComplaintsWriter())
                .build();
    }

    @Bean
    public Step loadOrders(
    ) {
        return stepBuilderFactory.get("load-orders")
                .<SFDCOrdersDTO, SFDCOrdersDTO>chunk(100)
                .reader(ordersReader(sfdcBatchDataDetailsRequest))
                .writer(new OrderWriter())
                .build();
    }

    @Bean
    public Step loadOrderItems(
    ) {
        return stepBuilderFactory.get("load-order-items")
                .<SFDCOrderItemsDTO, SFDCOrderItemsDTO>chunk(100)
                .reader(orderItemsReader(sfdcBatchDataDetailsRequest))
                .writer(new OrderItemsWriter())
                .build();
    }

    @Bean
    public Step loadOpportunityLineItems(
    ) {
        return stepBuilderFactory.get("load-order-items")
                .<SFDCOpportunityLineItemsDTO, SFDCOpportunityLineItemsDTO>chunk(100)
                .reader(opportunityLineItemsReader(sfdcBatchDataDetailsRequest))
                .writer(new OpportunityLineItemsWriter())
                .build();
    }

    @Bean
    public Step loadProposals(
    ) {
        return stepBuilderFactory.get("load-proposals")
                .<SFDCProposalsDTO, SFDCProposalsDTO>chunk(100)
                .reader(proposalsReader(sfdcBatchDataDetailsRequest))
                .writer(new ProposalWriter())
                .build();
    }

    @Bean
    public Step loadEligibleSparesServices(
    ) {
        return stepBuilderFactory.get("load-eligible-spares-services")
                .<SFDCEligibleSparesServicesDTO, SFDCEligibleSparesServicesDTO>chunk(100)
                .reader(eligibleSpareServiceReader(sfdcBatchDataDetailsRequest))
                .writer(new EligibleSpareServiceWriter())
                .build();
    }

    @Bean
    public Step loadAssetHistory(
    ) {
        return stepBuilderFactory.get("load-asset-history")
                .<SFDCAssetHistoryDTO, SFDCAssetHistoryDTO>chunk(100)
                .reader(assetHistoryReader(sfdcBatchDataDetailsRequest))
                .writer(new AssetHistoryWriter())
                .build();
    }

    @Bean
    public Job productsJob(JobBuilderFactory jobBuilderFactory
    ) {


        return getJobBuilder("load-products")
                .start(loadProducts())
                .build();
    }

    @Bean
    public Job accountsJob(JobBuilderFactory jobBuilderFactory
    ) {


        return getJobBuilder("load-accounts")
                .start(loadAccounts())
                .build();
    }

    @Bean
    public Job recommendationsJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-recommendations")
                .start(loadRecommendations())
                .build();
    }

    @Bean
    public Job servicesJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-services")
                .start(loadServices())
                .build();
    }

    @Bean
    public Job sparesJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-spares")
                .start(loadSpares())
                .build();
    }

    @Bean
    public Job assetsJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-assets")
                .start(loadAssets())
                .build();
    }

    @Bean
    public Job opportunitiesJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-opportunities")
                .start(loadOpportunities())
                .build();
    }

    @Bean
    public Job opportunityLineItemsJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-opportunity-line-items")
                .start(loadOpportunityLineItems())
                .build();
    }

    @Bean
    public Job pricebooksJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-pricebooks")
                .start(loadPricebooks())
                .build();
    }

    @Bean
    public Job pricebookEntriesJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-pricebookentries")
                .start(loadPricebookEntries())
                .build();
    }

    @Bean
    public Job usersJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-users")
                .start(loadUsers())
                .build();
    }

    @Bean
    public Job complaintsJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-complaints")
                .start(loadComplaints())
                .build();
    }

    @Bean
    public Job ordersJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-orders")
                .start(loadOrders())
                .build();
    }

    @Bean
    public Job orderItemsJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-order-items")
                .start(loadOrderItems())
                .build();
    }

    @Bean
    public Job proposalsJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-proposals")
                .start(loadProposals())
                .build();
    }

    @Bean
    public Job eligibleSpareServicesJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-eligible-spares-services")
                .start(loadEligibleSparesServices())
                .build();
    }

    @Bean
    public Job assetHistoryJob(JobBuilderFactory jobBuilderFactory
    ) {

        return getJobBuilder("load-asset-history")
                .start(loadAssetHistory())
                .build();
    }



    @Bean
    public ItemReader<SFDCProductInfoDTO> productItemReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new ProductItemReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCAccountInfoDTO> accountsItemReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new AccountItemReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCRecommendationsDTO> recommendationsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new RecommendationsReader(sfdcBatchDataDetailsRequest);
    }
    @Bean
    public ItemReader<SFDCServicesDTO> servicesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new ServicesReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCSparesDTO> sparesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new SparesReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCAssetDTO> assetsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new AssetReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCOpportunityDTO> opportunityReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new OpportunityReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCPricebookDTO> pricebookReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new PricebookReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCPricebookEntryDTO> pricebookEntryReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new PricebookEntryReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCUsersDTO> usersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new UsersReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCComplaintsDTO> complaintsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new ComplaintsReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCOrdersDTO> ordersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new OrderReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCOrderItemsDTO> orderItemsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new OrderItemsReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCOpportunityLineItemsDTO> opportunityLineItemsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new OpportunityLineItemsReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCProposalsDTO> proposalsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new ProposalsReader(sfdcBatchDataDetailsRequest);
    }

    @Bean
    public ItemReader<SFDCEligibleSparesServicesDTO> eligibleSpareServiceReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new EligibleSparesSerivcesReader(sfdcBatchDataDetailsRequest);
    }
    @Bean
    public ItemReader<SFDCAssetHistoryDTO> assetHistoryReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest) {

        return  new AssetHistoryReader(sfdcBatchDataDetailsRequest);
    }
    }
