package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class PricebookWriter implements ItemWriter<SFDCPricebookDTO> {

    final private CSVWrite csvWrite;

    final private EnquiryConnector enquiryConnector;

    public PricebookWriter(CSVWrite csvWrite,EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector=enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCPricebookDTO> productInfos) throws Exception {
        log.info("Saving data for pricebooks of size: {} ", productInfos.size());
        final String[] headers = new String[]{"id", "name", "description", "active"};
        final String fileName = "price_book.csv";
        final String apiName = "PriceBook";
        CompletableFuture<String> url = csvWrite.writeToCSV(productInfos, headers, fileName, apiName);
        log.info("Written pricebook to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
    }


}