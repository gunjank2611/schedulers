package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
@Log4j2
public class AssetHistoryWriter implements ItemWriter<SFDCAssetHistoryDTO> {

    @Override
    public void write(List<? extends SFDCAssetHistoryDTO> assetHistoryDTOS) throws Exception {
        log.info("Saving data for asset history of size: {}",assetHistoryDTOS.size());
    }
}