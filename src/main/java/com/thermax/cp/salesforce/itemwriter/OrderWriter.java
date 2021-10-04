package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class OrderWriter implements ItemWriter<SFDCOrdersDTO> {

    @Override
    public void write(List<? extends SFDCOrdersDTO> ordersDTOS) throws Exception {
        log.info("Saving data for orders of size: {} ", ordersDTOS.size());
    }
}