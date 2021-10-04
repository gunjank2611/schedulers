package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
public class ProductItemProcessor implements ItemProcessor<SFDCProductInfoDTO, SFDCProductInfoDTO> {

    @Override
    public SFDCProductInfoDTO process(SFDCProductInfoDTO sfdcProductInfoDTOs) throws Exception {

        return sfdcProductInfoDTOs;
    }
}
