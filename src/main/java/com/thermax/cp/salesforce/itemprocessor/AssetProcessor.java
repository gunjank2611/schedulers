package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.asset.AssetOwnerDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import org.springframework.batch.item.ItemProcessor;

public class AssetProcessor implements ItemProcessor<SFDCAssetDTO, SFDCAssetDTO> {

    @Override
    public SFDCAssetDTO process(SFDCAssetDTO sfdcAssetDTO){

        if(sfdcAssetDTO.getAssetOwnerDTO()!=null)
        {
            AssetOwnerDTO assetOwnerDTO=sfdcAssetDTO.getAssetOwnerDTO();
            sfdcAssetDTO.setOwnerName(assetOwnerDTO.getOwnerName());
            sfdcAssetDTO.setOwnerPhoneNumber(assetOwnerDTO.getMobilePhone());
            sfdcAssetDTO.setOwnerUserRoleName(assetOwnerDTO.getAssetUserRoleDTO().getUserRoleName());
        }

        return sfdcAssetDTO;
    }
}
