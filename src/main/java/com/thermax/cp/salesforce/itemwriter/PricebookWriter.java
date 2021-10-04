package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class PricebookWriter implements ItemWriter<SFDCPricebookDTO> {


    @Override
    public void write(List<? extends SFDCPricebookDTO> productInfos) throws Exception {
        log.info("Saving data for pricebooks of size: {} ", productInfos.size());
    }


}