package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class AssetWriter implements ItemWriter<SFDCAssetDTO> {



    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;




    public AssetWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCAssetDTO> assetDTOS) throws Exception {
        log.info("Received assets from SFDC : {}", assetDTOS.size());
        log.info("Written assets size : {}", assetDTOS.size());
        final String[] headers = new String[]{"id", "name", "installDate", "tHSC_Warranty_Expiry_Date__c","tHS_Asset_Service_By_From_ERP__c","ownerId",
                "accountId", "tH_IBG_Division__c", "tHCH_Region__c", "ownerId","ownerName","ownerUserRoleName","tHCH_Sales_Order_Number__c","asset_Make__c",
        "tHCH_Asset_Status__c","calorie_Potential__c","tHSC_I_C_Scope__c","tHS_Service_Sales_Engineer__c","tPF_User__c","tHS_WARRANTY_DUR_FR_COMM_DT__c",
        "tHS_WARRANTY_DUR_FR_DISP_DT__c","tHSC_First_Date_of_Dispatch__c","tH_Shipment_received_date__c","tH_IBG_Commissioning_Date__c",
        "revised_warranty_expiry_date__c","warranty_Revision_Status__c","reasonForExtendedWarranty","tHSC_Country__c","tHS_Order__r",
        "tH_When_to_Engage_Customer_days__c","createdDate","lastModifiedDate","tMAX_TCA_User__c","caUsername","caUserMobile","caUserRoleName",
        "serviceSpocName","serviceSpocMobile","serviceSpocUserRoleName","tMAX_TCA_User__c","tMAX_Service_SPOC_CP__c","tMAX_Spares_Sales_SPOC_CP__c",
        "spareSalesSpocName","spareSalesSpocMobile","spareSalesSpocUserRoleName","tMAX_Service_Sales_SPOC_CP__c","servicesSalesSpocName","serviceSalesSpocUserRoleName",
        "serviceSalesSpocMobile","contactId","contact","TMAX_Product_Family_CP__c"};
        final String fileName="assets.csv";
        final String apiName="Assets";
        CompletableFuture<String> url = csvWrite.writeToCSV(assetDTOS,headers,fileName,apiName);
        log.info("Written assets to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        fileURLDTO.setEndPoint("load-assets");
        fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
        assetsConnector.sendAssetsBlobUrl(fileURLDTO);
    }
}
