package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.ErpOrderStatusDTO;
import com.thermax.cp.salesforce.dto.orders.PageNumberDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.feign.request.ErpOrderStatusRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Log4j2
public class ErpOrderStatusReader implements ItemReader<SFDCOrderHeadersDTO> {

    private final ErpOrderStatusRequest erpOrderStatusRequest;
    private final String frequency;
    private boolean isOrderStatusInitialized;
    private List<SFDCOrderHeadersDTO> sfdcOrderHeadersDTOs;
    private int index = 0;
    private long totalRecords = 0;

    public ErpOrderStatusReader(ErpOrderStatusRequest erpOrderStatusRequest, String frequency) {
        this.erpOrderStatusRequest = erpOrderStatusRequest;
        this.frequency = frequency;
    }

    @Override
    public SFDCOrderHeadersDTO read() throws Exception {

        log.info("Inside ErpOrderStatusReader -> read()..");

        if (!isOrderStatusInitialized) {

            log.info("ErpOrderStatus is not initialized !!");

            long totalCount;

            // Initial PageRequest..
            int pageNumber = 1;
            PageNumberDTO pageNumberDTO = new PageNumberDTO(pageNumber);
            ResponseEntity<ErpOrderStatusDTO> erpOrderStatusHeaderResponseEntity =
                    erpOrderStatusRequest.fetchOrderStatus(pageNumberDTO);

            if (erpOrderStatusHeaderResponseEntity.hasBody()) {
                ErpOrderStatusDTO erpOrderStatusDTO = erpOrderStatusHeaderResponseEntity.getBody();
                totalCount = erpOrderStatusDTO.getTotalCount();
                totalCount = decrementTotalCount(totalCount, erpOrderStatusDTO.getOrdersList().size());

                // Loop until there are more order statuses available..
                while (totalCount > 0) {
                    ResponseEntity<ErpOrderStatusDTO> nextErpOrderStatusResponseEntity =
                            erpOrderStatusRequest.fetchOrderStatus(new PageNumberDTO(++pageNumber));
                    if (nextErpOrderStatusResponseEntity.hasBody()) {
                        ErpOrderStatusDTO nextErpOrderStatusDTO = nextErpOrderStatusResponseEntity.getBody();
                        erpOrderStatusDTO.getOrdersList().addAll(nextErpOrderStatusDTO.getOrdersList());
                        totalCount = decrementTotalCount(totalCount, nextErpOrderStatusDTO.getOrdersList().size());
                    }
                }
                sfdcOrderHeadersDTOs = erpOrderStatusDTO.getOrdersList();
                totalRecords = erpOrderStatusDTO.getTotalCount();
            }

            log.info("Total record(s) fetched from ERP Endpoint -> " + totalRecords);
            isOrderStatusInitialized = true;
        }

        SFDCOrderHeadersDTO sfdcOrderHeadersDTO;
        if (index < totalRecords) {
            sfdcOrderHeadersDTO = sfdcOrderHeadersDTOs.get(index);
            index++;
        } else {
            sfdcOrderHeadersDTO = null;
        }

        return sfdcOrderHeadersDTO;
    }

    private long decrementTotalCount(long totalCount, long nextSize) {
        return (totalCount - nextSize);
    }
}
