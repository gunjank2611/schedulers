package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class ProductWriter implements ItemWriter<SFDCProductInfoDTO> {



    @Override
    public void write(List<? extends SFDCProductInfoDTO> productInfos) throws Exception {
        log.info("Saving data for products of size:",productInfos.size());
    }
}