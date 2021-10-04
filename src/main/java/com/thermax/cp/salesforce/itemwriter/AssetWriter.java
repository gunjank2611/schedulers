package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class AssetWriter implements ItemWriter<SFDCAssetDTO> {

    @Override
    public void write(List<? extends SFDCAssetDTO> assetDTOS) throws Exception {
        log.info("Saving data for assets of size:",assetDTOS.size());
    }
}
