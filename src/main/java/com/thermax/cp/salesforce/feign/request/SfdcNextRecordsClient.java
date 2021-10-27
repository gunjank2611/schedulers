package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.account.AccountDetailsListDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryListDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesListDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCOpportunityContactRoleDTOList;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintListDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsListDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTOList;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsListDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersItemsListDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersListDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTOList;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryListDTO;
import com.thermax.cp.salesforce.dto.product.ProductListDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsListDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsListDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogListDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesListDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesListDTO;
import com.thermax.cp.salesforce.dto.users.SFDCUserDTOList;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersListDTO;
import com.thermax.cp.salesforce.feign.config.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "SfdcNextRecordsFeignClient", url = "${feign.client.salesforce-url}", configuration = FeignRequestConfiguration.class)
public interface SfdcNextRecordsClient {


    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<ProductListDTO> loadProductDetails(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<AccountDetailsListDTO> loadAccountDetails(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCRecommendationsListDTO> loadRecommendations(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCServicesListDTO> loadServices(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCSparesListDTO> loadSpares(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCAssetDTOList> loadAssets(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCAssetHistoryListDTO> loadAssetHistory(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCOpportunityDTOList> loadOpportunities(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCPricebookDTOList> loadPricebooks(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCPricebookEntryListDTO> loadPricebookEntries(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCUserDTOList> loadUsers(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCComplaintListDTO> loadComplaints(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCOrdersListDTO> loadOrders(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCOrdersItemsListDTO> loadOrderItems(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCOpportunityContactRoleDTOList> loadOpportunityContactRole(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCOpportunityLineItemsListDTO> loadOpportunityLineItems(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCProposalsListDTO> loadProposals(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCEligibleSparesServicesListDTO> loadEligibleSparesServices(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCContactsListDTO> loadContacts(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<SFDCServiceLogListDTO> loadServiceLog(@PathVariable String nextUrl);

    @GetMapping("/data/v52.0/query/{nextUrl}")
    ResponseEntity<ThermaxUsersListDTO> loadThermaxUsers(@PathVariable String nextUrl);
}
