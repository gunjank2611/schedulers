package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import org.springframework.batch.item.ItemProcessor;

public class SparesProcessor implements ItemProcessor<SFDCSparesDTO, SFDCSparesDTO> {

    @Override
    public SFDCSparesDTO process(SFDCSparesDTO sfdcSparesDTO) throws Exception {

        if(sfdcSparesDTO.getDescription()!=null) {
            String description=sfdcSparesDTO.getDescription().replace("\n", "").replace("\r", "")
                    .replaceAll(",","~");
            sfdcSparesDTO.setDescription(description);
        }
        return sfdcSparesDTO;
    }
}
