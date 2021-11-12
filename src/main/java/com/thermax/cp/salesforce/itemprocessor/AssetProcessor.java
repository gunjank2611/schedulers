package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.asset.*;
import com.thermax.cp.salesforce.dto.commons.OwnerDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import org.springframework.batch.item.ItemProcessor;

public class AssetProcessor implements ItemProcessor<SFDCAssetDTO, SFDCAssetDTO> {

    @Override
    public SFDCAssetDTO process(SFDCAssetDTO sfdcAssetDTO){

        if(sfdcAssetDTO!=null)
        {
            OwnerDTO assetOwnerDTO=sfdcAssetDTO.getAssetOwnerDTO();
            if(assetOwnerDTO!=null) {
                sfdcAssetDTO.setOwnerName(assetOwnerDTO.getOwnerName());
                sfdcAssetDTO.setOwnerMobile(assetOwnerDTO.getMobilePhone());
                sfdcAssetDTO.setOwnerUserRoleName(assetOwnerDTO.getUserRole()!=null?assetOwnerDTO.getUserRole().getUserRoleName():"");
                sfdcAssetDTO.setOwnerEmail(sfdcAssetDTO.getOwnerEmail());
            }
            TMAXTCAUserRDTO tmaxtcaUserRDTO=sfdcAssetDTO.getTMAX_TCA_User__r();
            if(tmaxtcaUserRDTO!=null) {
                sfdcAssetDTO.setCaUsername(tmaxtcaUserRDTO.getName());
                sfdcAssetDTO.setCaUserMobile(tmaxtcaUserRDTO.getMobilePhone());
                sfdcAssetDTO.setCaUserRoleName(tmaxtcaUserRDTO.getUserRole()!=null ?tmaxtcaUserRDTO.getUserRole().getUserRoleName():"");
            }
            TMAXServiceSPOCCPRDTO tmaxServiceSPOCCPRDTO=sfdcAssetDTO.getTMAX_Service_SPOC_CP__r();
            if(tmaxServiceSPOCCPRDTO!=null) {
                sfdcAssetDTO.setServiceSpocName(tmaxServiceSPOCCPRDTO.getName());
                sfdcAssetDTO.setServiceSpocMobile(tmaxServiceSPOCCPRDTO.getMobilePhone());
                sfdcAssetDTO.setServiceSpocUserRoleName(tmaxServiceSPOCCPRDTO.getUserRole()!=null ?tmaxServiceSPOCCPRDTO.getUserRole().getUserRoleName():"");
                sfdcAssetDTO.setServiceSpocEmail(tmaxServiceSPOCCPRDTO.getEmail());
            }
            TMAXSparesSalesSPOCCPRDTO tmaxSparesSalesSPOCCPRDTO=sfdcAssetDTO.getTMAX_Spares_Sales_SPOC_CP__r();
            if(tmaxSparesSalesSPOCCPRDTO!=null) {
                sfdcAssetDTO.setSpareSalesSpocName(tmaxSparesSalesSPOCCPRDTO.getName());
                sfdcAssetDTO.setSpareSalesSpocMobile(tmaxSparesSalesSPOCCPRDTO.getMobilePhone());
                sfdcAssetDTO.setSpareSalesSpocUserRoleName(tmaxSparesSalesSPOCCPRDTO.getUserRole()!=null?tmaxSparesSalesSPOCCPRDTO.getUserRole().getUserRoleName():"");
                sfdcAssetDTO.setSpareSalesSpocEmail(tmaxSparesSalesSPOCCPRDTO.getEmail());
            }
            TMAXServiceSalesSPOCCPRDTO tmaxServiceSalesSPOCCPRDTO=sfdcAssetDTO.getTMAX_Service_Sales_SPOC_CP__r();
            if(tmaxServiceSalesSPOCCPRDTO!=null) {
                sfdcAssetDTO.setServicesSalesSpocName(tmaxServiceSalesSPOCCPRDTO.getName());
                sfdcAssetDTO.setServiceSpocMobile(tmaxServiceSalesSPOCCPRDTO.getMobilePhone());
                sfdcAssetDTO.setServiceSalesSpocUserRoleName(tmaxServiceSalesSPOCCPRDTO.getUserRole()!=null?tmaxServiceSalesSPOCCPRDTO.getUserRole().getUserRoleName():"");
                sfdcAssetDTO.setServiceSalesSpocEmail(tmaxServiceSalesSPOCCPRDTO.getEmail());
            }
        }
        return sfdcAssetDTO;
    }
}
