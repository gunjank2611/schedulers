package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.commons.OwnerDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import org.springframework.batch.item.ItemProcessor;

public class ComplaintsProcessor implements ItemProcessor<SFDCComplaintsDTO,SFDCComplaintsDTO> {
    @Override
    public SFDCComplaintsDTO process(SFDCComplaintsDTO sfdcComplaintsDTO) throws Exception {
        OwnerDTO sfdcComplaintsOwnerDTO=sfdcComplaintsDTO.getOwner();
        if(sfdcComplaintsOwnerDTO!=null)
        {
            sfdcComplaintsDTO.setOwnerName(sfdcComplaintsOwnerDTO.getOwnerName());
            sfdcComplaintsDTO.setOwnerEmail(sfdcComplaintsOwnerDTO.getEmail());
        }
        if(sfdcComplaintsDTO.getDescription()!=null) {
            String description=sfdcComplaintsDTO.getDescription().replace("\n", "").replace("\r", "");
            sfdcComplaintsDTO.setDescription(description);
        }
        return sfdcComplaintsDTO;
    }
}
