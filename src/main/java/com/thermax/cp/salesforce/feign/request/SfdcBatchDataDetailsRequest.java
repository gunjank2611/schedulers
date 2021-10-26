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
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SfdcBatchDetailsRequestFeignClient", url = "${feign.client.salesforce-url}", configuration = FeignRequestConfiguration.class)
public interface SfdcBatchDataDetailsRequest {


    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<ProductListDTO> loadProductDetails(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<AccountDetailsListDTO> loadAccountDetails(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCRecommendationsListDTO> loadRecommendations(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCServicesListDTO> loadServices(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCSparesListDTO> loadSpares(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCAssetDTOList> loadAssets(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCAssetHistoryListDTO> loadAssetHistory(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCOpportunityDTOList> loadOpportunities(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCPricebookDTOList> loadPricebooks(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCPricebookEntryListDTO> loadPricebookEntries(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCUserDTOList> loadUsers(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCComplaintListDTO> loadComplaints(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCOrdersListDTO> loadOrders(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCOrdersItemsListDTO> loadOrderItems(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCOpportunityContactRoleDTOList> loadOpportunityContactRole(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCOpportunityLineItemsListDTO> loadOpportunityLineItems(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCProposalsListDTO> loadProposals(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCEligibleSparesServicesListDTO> loadEligibleSparesServices(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCContactsListDTO> loadContacts(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<SFDCServiceLogListDTO> loadServiceLog(@RequestParam("q") String query);

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<ThermaxUsersListDTO> loadThermaxUsers(@RequestParam("q") String query);
}
