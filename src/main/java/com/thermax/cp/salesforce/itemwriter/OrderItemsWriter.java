package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.orders.SFDCOrderItemsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class OrderItemsWriter implements ItemWriter<SFDCOrderItemsDTO> {

    @Override
    public void write(List<? extends SFDCOrderItemsDTO> orderItemDTOs) throws Exception {
        log.info("Saving data for order items of size: {} ", orderItemDTOs.size());
    }
}
