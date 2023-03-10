package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.AsyncOrderStatusReadWriter;
import com.thermax.cp.salesforce.dto.orders.OrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.mapper.OrdersMapper;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.item.ItemWriter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OrderWriter implements ItemWriter<SFDCOrdersDTO> {

    final private CSVWrite csvWrite;
    final private EnquiryConnector enquiryConnector;
    private final OrdersMapper ordersMapper = Mappers.getMapper(OrdersMapper.class);
    final private AsyncOrderStatusReadWriter asyncOrderStatusReadWriter;

    public OrderWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector, AsyncOrderStatusReadWriter asyncOrderStatusReadWriter) {
        this.csvWrite = csvWrite;
        this.enquiryConnector = enquiryConnector;
        this.asyncOrderStatusReadWriter = asyncOrderStatusReadWriter;
    }

    @Override
    public void write(List<? extends SFDCOrdersDTO> ordersDTOS) throws Exception {
        log.info("Received Orders from SFDC of size : {}", ordersDTOS.size());
        final String[] headers = new String[]{"id", "orderNumber", "tHCMG_Customer_PO__c", "accountId", "tHCMG_Payment_Term__c",
                "tHCMG_ERP_Operating_Unit__c", "tHCMG_Cheque_Number__c", "tHCMG_Transaction_Type_Id__c", "tHCMG_Bill_To_Location__c", "tHCMG_Warehouse__c",
                "tHCMG_Ship_To_Location__c", "tHCMG_Date_Ordered__c", "tHCMG_Payment_Type__c", "effectiveDate", "totalAmount", "tHCMG_FOB__c", "tH_Division__c",
                "tHCMG_Freight_Terms__c", "status", "opportunityId", "eRP_Order_Number__c", "tH_Opportunity_Number__c", "asset__c", "ownerId", "ownerName", "ownerEmail", "ownerContact", "ownerRole", "opportunityType"};
        final String fileName = "Orders.csv";
        final String apiName = "Orders";
        try {
            if (ordersDTOS != null && !ordersDTOS.isEmpty()) {
                log.info("Mapping response for writing...");
                List<OrdersDTO> orders = ordersMapper.convertToOrdersFromSFDCOrdersList((List<SFDCOrdersDTO>) ordersDTOS);
                log.info("Writing response to CSV...");
                CompletableFuture<String> url = csvWrite.writeToCSV(orders, headers, fileName, apiName);
                String ordersBlobUrl = url.get();
                if (ordersBlobUrl != null) {
                    FileURLDTO fileURLDTO = new FileURLDTO();
                    fileURLDTO.setFileUrl(ordersBlobUrl);
                    fileURLDTO.setEndPoint("load-orders");
                    fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
                    log.info("Pushing data to respective microservice for consumption and DB persisting...");
                    enquiryConnector.sendOrdersBlobUrl(fileURLDTO);
                    log.info("Pushing data process completed!");
                } else {
                    log.error("Error while getting orders Blob URL!");
                }
            } else {
                log.info("No data to write for order status!");
            }
        } catch (Exception e) {
            log.error("Error while processing order: {}", e.getMessage());
        }
    }
}