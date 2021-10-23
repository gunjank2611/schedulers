package com.thermax.cp.salesforce.config;

import com.thermax.cp.salesforce.AsyncOrderStatusReadWriter;
import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOpportunityContactRoleDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderItemsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersDTO;
import com.thermax.cp.salesforce.feign.connectors.AccountsConnector;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.feign.request.SfdcOrdersRequest;
import com.thermax.cp.salesforce.itemprocessor.*;
import com.thermax.cp.salesforce.itemreader.*;
import com.thermax.cp.salesforce.itemwriter.*;
import com.thermax.cp.salesforce.utils.CSVWrite;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private SfdcOrdersRequest sfdOrdersRequest;
    @Autowired
    private CSVWrite csvWrite;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private AssetsConnector assetsConnector;
    @Autowired
    private AccountsConnector accountsConnector;
    @Autowired
    private EnquiryConnector enquiryConnector;
    private String frequency;
    private String url;
    @Autowired
    private AsyncOrderStatusReadWriter asyncOrderStatusReadWriter;

    public JobParametersIncrementer jobParametersIncrementer() {
        return new RunIdIncrementer();
    }

    public JobBuilder getJobBuilder(String jobName) {
        return jobBuilderFactory.get(jobName)
                .incrementer(jobParametersIncrementer());
    }

    @Bean
    public Step loadProducts() {
        return stepBuilderFactory.get("load-products")
                .<SFDCProductInfoDTO, SFDCProductInfoDTO>chunk(100)
                .reader(productItemReader(sfdcBatchDataDetailsRequest, frequency))
                .processor(new ProductItemProcessor())
                .writer(new ProductWriter(csvWrite,enquiryConnector))
                .build();
    }

    @Bean
    public Step loadAccounts() {
        return stepBuilderFactory.get("load-accounts")
                .<SFDCAccountInfoDTO, SFDCAccountInfoDTO>chunk(100)
                .reader(accountsItemReader(sfdcBatchDataDetailsRequest, frequency))
                .processor(new AccountsProcessor())
                .writer(new AccountsDBWriter(csvWrite,accountsConnector))
                .build();
    }

    @Bean
    public Step loadRecommendations() {
        return stepBuilderFactory.get("load-recommendations")
                .<SFDCRecommendationsDTO, SFDCRecommendationsDTO>chunk(100)
                .reader(recommendationsReader(sfdcBatchDataDetailsRequest, frequency))
                .processor(new RecommendationsProcessor())
                .writer(new RecommendationsWriter(csvWrite, assetsConnector))
                .build();
    }

    @Bean
    public Step loadServices() {
        return stepBuilderFactory.get("load-services")
                .<SFDCServicesDTO, SFDCServicesDTO>chunk(100)
                .reader(servicesReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new ServicesWriter(csvWrite, assetsConnector))
                .build();
    }

    @Bean
    public Step loadSpares() {
        return stepBuilderFactory.get("load-spares")
                .<SFDCSparesDTO, SFDCSparesDTO>chunk(100)
                .reader(sparesReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new SparesWriter(csvWrite, assetsConnector))
                .build();
    }

    @Bean
    public Step loadAssets() {
        return stepBuilderFactory.get("load-assets")
                .<SFDCAssetDTO, SFDCAssetDTO>chunk(100)
                .reader(assetsReader(sfdcBatchDataDetailsRequest, frequency))
                .processor(new AssetProcessor())
                .writer(new AssetWriter(csvWrite, assetsConnector))
                .build();
    }

    @Bean
    public Step loadOpportunities() {
        return stepBuilderFactory.get("load-opportunities")
                .<SFDCOpportunityDTO, SFDCOpportunityDTO>chunk(100)
                .reader(opportunityReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new OpportunityWriter(csvWrite,enquiryConnector))
                .build();
    }

    @Bean
    public Step loadPricebooks() {
        return stepBuilderFactory.get("load-pricebooks")
                .<SFDCPricebookDTO, SFDCPricebookDTO>chunk(100)
                .reader(pricebookReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new PricebookWriter(csvWrite,enquiryConnector))
                .build();
    }

    @Bean
    public Step loadPricebookEntries() {
        return stepBuilderFactory.get("load-pricebookentries")
                .<SFDCPricebookEntryDTO, SFDCPricebookEntryDTO>chunk(100)
                .reader(pricebookEntryReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new PricebookEntryWriter(csvWrite,enquiryConnector))
                .build();
    }

    @Bean
    public Step loadUsers() {
        return stepBuilderFactory.get("load-users")
                .<SFDCUsersDTO, SFDCUsersDTO>chunk(100)
                .reader(usersReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new UsersWriter())
                .build();
    }

    @Bean
    public Step loadComplaints() {
        return stepBuilderFactory.get("load-complaints")
                .<SFDCComplaintsDTO, SFDCComplaintsDTO>chunk(100)
                .reader(complaintsReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new ComplaintsWriter(csvWrite,enquiryConnector))
                .build();
    }

    @Bean
    public Step loadOrders() {
        return stepBuilderFactory.get("load-orders")
                .<SFDCOrdersDTO, SFDCOrdersDTO>chunk(100)
                .reader(ordersReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new OrderWriter(csvWrite, enquiryConnector, asyncOrderStatusReadWriter))
                .build();
    }

    @Bean
    public Step loadOrderItems() {
        return stepBuilderFactory.get("load-order-items")
                .<SFDCOrderItemsDTO, SFDCOrderItemsDTO>chunk(100)
                .reader(orderItemsReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new OrderItemsWriter(csvWrite, enquiryConnector))
                .build();
    }

    @Bean
    public Step loadOpportunityContactRole() {
        return stepBuilderFactory.get("load-opportunity-contact-role")
                .<SFDCOpportunityContactRoleDTO, SFDCOpportunityContactRoleDTO>chunk(100)
                .reader(opportunityContactRoleReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new OpportunityContactRoleWriter(csvWrite, enquiryConnector))
                .build();
    }

    @Bean
    public Step loadOpportunityLineItems() {
        return stepBuilderFactory.get("load-opportunity-line-items")
                .<SFDCOpportunityLineItemsDTO, SFDCOpportunityLineItemsDTO>chunk(100)
                .reader(opportunityLineItemsReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new OpportunityLineItemsWriter(csvWrite,enquiryConnector))
                .build();
    }

    @Bean
    public Step loadProposals() {
        return stepBuilderFactory.get("load-proposals")
                .<SFDCProposalsDTO, SFDCProposalsDTO>chunk(100)
                .reader(proposalsReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new ProposalWriter(csvWrite))
                .build();
    }

    @Bean
    public Step loadEligibleSparesServices() {
        return stepBuilderFactory.get("load-eligible-spares-services")
                .<SFDCEligibleSparesServicesDTO, SFDCEligibleSparesServicesDTO>chunk(100)
                .reader(eligibleSpareServiceReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new EligibleSpareServiceWriter(csvWrite, enquiryConnector))
                .build();
    }

    @Bean
    public Step loadAssetHistory() {
        return stepBuilderFactory.get("load-asset-history")
                .<SFDCAssetHistoryDTO, SFDCAssetHistoryDTO>chunk(100)
                .reader(assetHistoryReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new AssetHistoryWriter(csvWrite,assetsConnector))
                .build();
    }

    @Bean
    public Step loadContacts() {
        return stepBuilderFactory.get("load-contacts")
                .<SFDCContactsDTO, SFDCContactsDTO>chunk(100)
                .reader(contactsReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new ContactsWriter())
                .build();
    }

    @Bean
    public Step loadServiceLog() {
        return stepBuilderFactory.get("load-service-log")
                .<SFDCServiceLogDTO, SFDCServiceLogDTO>chunk(100)
                .reader(serviceLogReader(sfdcBatchDataDetailsRequest, frequency))
                .writer(new ServiceLogWriter(csvWrite,assetsConnector))
                .build();
    }

    @Bean
    public Step loadThermaxUsers() {
        return stepBuilderFactory.get("load-thermax-users")
                .<ThermaxUsersDTO, ThermaxUsersDTO>chunk(100)
                .reader(thermaxUsersReader(sfdcBatchDataDetailsRequest, frequency))
                .processor(new ThermaxUserProcessor())
                .writer(new ThermaxUsersWriter(csvWrite,enquiryConnector))
                .build();
    }


    @Bean
    public Job productsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-products")
                .start(loadProducts())
                .build();
    }

    @Bean
    public Job accountsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-accounts")
                .start(loadAccounts())
                .build();
    }

    @Bean
    public Job recommendationsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-recommendations")
                .start(loadRecommendations())
                .build();
    }

    @Bean
    public Job servicesJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-services")
                .start(loadServices())
                .build();
    }

    @Bean
    public Job sparesJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-spares")
                .start(loadSpares())
                .build();
    }

    @Bean
    public Job assetsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-assets")
                .start(loadAssets())
                .build();
    }

    @Bean
    public Job opportunitiesJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-opportunities")
                .start(loadOpportunities())
                .build();
    }

    @Bean
    public Job opportunityLineItemsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-opportunity-line-items")
                .start(loadOpportunityLineItems())
                .build();
    }

    @Bean
    public Job pricebooksJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-pricebooks")
                .start(loadPricebooks())
                .build();
    }

    @Bean
    public Job pricebookEntriesJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-pricebookentries")
                .start(loadPricebookEntries())
                .build();
    }

    @Bean
    public Job usersJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-users")
                .start(loadUsers())
                .build();
    }

    @Bean
    public Job complaintsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-complaints")
                .start(loadComplaints())
                .build();
    }

    @Bean
    public Job ordersJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-orders")
                .start(loadOrders())
                .build();
    }

    @Bean
    public Job orderItemsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-order-items")
                .start(loadOrderItems())
                .build();
    }

    @Bean
    public Job opportunityContactRoleJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-opportunity-contact-role")
                .start(loadOpportunityContactRole())
                .build();
    }

    @Bean
    public Job proposalsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-proposals")
                .start(loadProposals())
                .build();
    }

    @Bean
    public Job eligibleSpareServicesJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-eligible-spares-services")
                .start(loadEligibleSparesServices())
                .build();
    }

    @Bean
    public Job assetHistoryJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-asset-history")
                .start(loadAssetHistory())
                .build();
    }

    @Bean
    public Job contactsJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-contacts")
                .start(loadContacts())
                .build();
    }

    @Bean
    public Job serviceLogJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-service-log")
                .start(loadServiceLog())
                .build();
    }


    @Bean
    public Job thermaxUsersJob(JobBuilderFactory jobBuilderFactory) {
        return getJobBuilder("load-service-log")
                .start(loadThermaxUsers())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<SFDCProductInfoDTO> productItemReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                            @Value("#{jobParameters[frequency]}") String frequency) {
        return new ProductItemReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCAccountInfoDTO> accountsItemReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                             @Value("#{jobParameters[frequency]}") String frequency) {
        return new AccountItemReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCRecommendationsDTO> recommendationsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                                    @Value("#{jobParameters[frequency]}") String frequency) {
        return new RecommendationsReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCServicesDTO> servicesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                      @Value("#{jobParameters[frequency]}") String frequency) {
        return new ServicesReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCSparesDTO> sparesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                  @Value("#{jobParameters[frequency]}") String frequency) {
        return new SparesReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCAssetDTO> assetsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                 @Value("#{jobParameters[frequency]}") String frequency) {
        return new AssetReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCOpportunityDTO> opportunityReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                            @Value("#{jobParameters[frequency]}") String frequency) {
        return new OpportunityReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCPricebookDTO> pricebookReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                        @Value("#{jobParameters[frequency]}") String frequency) {
        return new PricebookReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCPricebookEntryDTO> pricebookEntryReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                                  @Value("#{jobParameters[frequency]}") String frequency) {
        return new PricebookEntryReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCUsersDTO> usersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                @Value("#{jobParameters[frequency]}") String frequency) {
        return new UsersReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCComplaintsDTO> complaintsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                          @Value("#{jobParameters[frequency]}") String frequency) {
        return new ComplaintsReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCOrdersDTO> ordersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                  @Value("#{jobParameters[frequency]}") String frequency) {
        return new OrderReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @Bean
    @StepScope
    public ItemReader<SFDCOrderItemsDTO> orderItemsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                          @Value("#{jobParameters[frequency]}") String frequency) {
        return new OrderItemsReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @Bean
    @StepScope
    public ItemReader<SFDCOpportunityContactRoleDTO> opportunityContactRoleReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                                                  @Value("#{jobParameters[frequency]}") String frequency) {
        return new OpportunityContactRoleReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCOpportunityLineItemsDTO> opportunityLineItemsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                                              @Value("#{jobParameters[frequency]}") String frequency) {
        return new OpportunityLineItemsReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @Bean
    @StepScope
    public ItemReader<SFDCProposalsDTO> proposalsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                        @Value("#{jobParameters[frequency]}") String frequency) {
        return new ProposalsReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCEligibleSparesServicesDTO> eligibleSpareServiceReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                                                @Value("#{jobParameters[frequency]}") String frequency) {
        return new EligibleSparesSerivcesReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCAssetHistoryDTO> assetHistoryReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                              @Value("#{jobParameters[frequency]}") String frequency) {
        return new AssetHistoryReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCContactsDTO> contactsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                      @Value("#{jobParameters[frequency]}") String frequency) {
        return new ContactsReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<SFDCServiceLogDTO> serviceLogReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                          @Value("#{jobParameters[frequency]}") String frequency) {
        return new ServiceLogReader(sfdcBatchDataDetailsRequest, frequency);
    }

    @StepScope
    @Bean
    public ItemReader<ThermaxUsersDTO> thermaxUsersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,
                                                          @Value("#{jobParameters[frequency]}") String frequency) {
        return new ThermaxUsersReader(sfdcBatchDataDetailsRequest, frequency);
    }
}