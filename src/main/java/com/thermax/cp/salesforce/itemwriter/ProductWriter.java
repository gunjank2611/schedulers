package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
public class ProductWriter implements ItemWriter<SFDCProductInfoDTO> {

    final private CSVWrite csvWrite;
    final private EnquiryConnector enquiryConnector;


    public ProductWriter(CSVWrite csvWrite,EnquiryConnector enquiryConnector)
    {
        this.csvWrite=csvWrite;
        this.enquiryConnector=enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCProductInfoDTO> productInfoDTOS) throws Exception {
        log.info("Received products from SFDC : {}", productInfoDTOS.size());
        log.info("Written products  size : {}", productInfoDTOS.size());
        final String[] headers = new String[]{"id", "name", "uom", "description","active","productCode"};
        final String fileName="products.csv";
        final String apiName="Products";
        CompletableFuture<String> url = csvWrite.writeToCSV(productInfoDTOS,headers,fileName,apiName);
        log.info("Written products to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        fileURLDTO.setEndPoint("load-products");
        fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
        //enquiryConnector.sendServiceLogUrl(fileURLDTO);
    }


}