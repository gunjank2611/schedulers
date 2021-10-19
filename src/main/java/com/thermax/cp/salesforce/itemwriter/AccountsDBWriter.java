package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AccountsConnector;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
public class AccountsDBWriter implements ItemWriter<SFDCAccountInfoDTO> {

    final private CSVWrite csvWrite;
    final private AccountsConnector accountsConnector;

    public AccountsDBWriter(CSVWrite csvWrite, AccountsConnector accountsConnector) {
        this.csvWrite = csvWrite;
        this.accountsConnector = accountsConnector;
    }

    @Override
    public void write(List<? extends SFDCAccountInfoDTO> accountInfoDTOS) throws Exception {
        log.info("Received accounts from SFDC : {}", accountInfoDTOS.size());
        final String[] headers = new String[]{"accountId", "name", "type", "website", "accountSource","customerForWaterDivision",
                "customerForHeatingDivision", "accountNumber", "erpOperatingUnit", "rating", "status", "cinNumber", "gstNumber",
                "panNumber", "parentId", "phone", "emailAddress", "active"};
        final String fileName="accounts.csv";
        final String apiName="Accounts";
        CompletableFuture<String> url = csvWrite.writeToCSV(accountInfoDTOS,headers,fileName,apiName);
        log.info("Written accounts to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        accountsConnector.sendAccountBlobUrl(fileURLDTO);
    }
}
