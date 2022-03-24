package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.commons.OwnerDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityAssetDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;

import org.springframework.batch.item.ItemProcessor;

public class OpportunityProcessor implements ItemProcessor<SFDCOpportunityDTO,SFDCOpportunityDTO> {

    @Override
    public SFDCOpportunityDTO process(SFDCOpportunityDTO sfdcOpportunityDTO) throws Exception {
        OwnerDTO sfdcOpportunityDTOOpportunityOwner=sfdcOpportunityDTO.getOpportunityOwner();
        if(sfdcOpportunityDTOOpportunityOwner!=null)
        {
            sfdcOpportunityDTO.setOwnerName(sfdcOpportunityDTOOpportunityOwner.getOwnerName());
            sfdcOpportunityDTO.setOwnerEmail(sfdcOpportunityDTOOpportunityOwner.getEmail());
            sfdcOpportunityDTO.setMobilePhone(sfdcOpportunityDTOOpportunityOwner.getMobilePhone());
            if(sfdcOpportunityDTOOpportunityOwner.getUserRole()!=null) {
                sfdcOpportunityDTO.setUserRoleName(sfdcOpportunityDTOOpportunityOwner.getUserRole().getUserRoleName());
            }
        }

        if(sfdcOpportunityDTO.getName()!=null) {
            String name=sfdcOpportunityDTO.getName().replaceAll("\"", "")
                    .replaceAll(",","~");;
            sfdcOpportunityDTO.setName(name);
        }
        if(sfdcOpportunityDTO.getOpportunityAsset()!=null)
        {
            sfdcOpportunityDTO.setAssetId(sfdcOpportunityDTO.getOpportunityAsset().getRecords().get(0).getAssetId());
        }
        sfdcOpportunityDTO.setRecordName(sfdcOpportunityDTO.getRecordType().getName());
        return sfdcOpportunityDTO;
    }
}
