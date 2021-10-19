package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class PricebookEntryWriter implements ItemWriter<SFDCPricebookEntryDTO> {


    final private CSVWrite csvWrite;

    public PricebookEntryWriter(CSVWrite csvWrite) {
        this.csvWrite = csvWrite;
    }

    @Override
    public void write(List<? extends SFDCPricebookEntryDTO> pricebookEntryDTOS) throws Exception {
        log.info("Saving data for pricebook entrirs of size: {} ", pricebookEntryDTOS.size());
        final String[] headers = new String[]{"id", "name", "currencyIsoCode", "lastModifiedDate", "pricebook2Id", "product2Id", "productCode", "systemModstamp", "unitPrice", "useStandardPrice", "active"};
        final String fileName = "price_book_entry.csv";
        final String apiName = "PriceBookEntry";
        CompletableFuture<String> url = csvWrite.writeToCSV(pricebookEntryDTOS, headers, fileName, apiName);
        log.info("Written pricebook entry to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
    }
}
