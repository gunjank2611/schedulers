package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OrderHeaderWriter implements ItemWriter<OrderHeadersDTO>{
    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;


    public OrderHeaderWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends OrderHeadersDTO> orderHeaderDTOS) throws Exception {
        log.info("Received assets from SFDC : {}", orderHeaderDTOS.size());
        log.info("Written assets size : {}", orderHeaderDTOS.size());
        final String[] headers = new String[]{"headerStatus", "expectedDeliveryDate"};
        final String fileName="orderstatus.csv";
        final String apiName="OrdersStaus";
        CompletableFuture<String> url = csvWrite.writeToCSV(orderHeaderDTOS,headers,fileName,apiName);
        log.info("Written assets to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        assetsConnector.sendRecommendationBlobUrl(fileURLDTO);
    }
}
