package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.ErpOrderStatusDTO;
import com.thermax.cp.salesforce.dto.orders.PageNumberDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
import com.thermax.cp.salesforce.feign.request.ErpOrderStatusRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ErpOrderStatusReader implements ItemReader<SFDCOrderHeadersDTO> {

    private final ErpOrderStatusRequest erpOrderStatusRequest;
    private final String frequency;
    private boolean isOrderStatusInitialized;
    private List<SFDCOrderHeadersDTO> sfdcOrderHeadersDTOs;
    private int nextErpOrderIndex = 0;
    private long totalRecords = 0;

    public ErpOrderStatusReader(ErpOrderStatusRequest erpOrderStatusRequest, String frequency) {
        this.erpOrderStatusRequest = erpOrderStatusRequest;
        this.frequency = frequency;
        this.nextErpOrderIndex=0;
    }
    @Override
    public SFDCOrderHeadersDTO read() throws Exception {

        if(isOrderStatusInitialized())
        {
            sfdcOrderHeadersDTOs =getERPOrderRecords(frequency);
        }
        SFDCOrderHeadersDTO nextErpOrder;
        if (nextErpOrderIndex < sfdcOrderHeadersDTOs.size()) {
            nextErpOrder = sfdcOrderHeadersDTOs.get(nextErpOrderIndex);
            nextErpOrderIndex++;
        }
        else {
            nextErpOrderIndex = 0;
            nextErpOrder = null;
        }

        return nextErpOrder;
    }

    private boolean isOrderStatusInitialized()
    {
        return this.sfdcOrderHeadersDTOs ==null;
    }


    private List<SFDCOrderHeadersDTO> getERPOrderRecords(String frequency) {
        long totalCount;
        int pageNumber = 1;
        PageNumberDTO pageNumberDTO = new PageNumberDTO(pageNumber);
        ResponseEntity<ErpOrderStatusDTO> erpOrderStatusHeaderResponseEntity =
                erpOrderStatusRequest.fetchOrderStatus(pageNumberDTO);
        List<SFDCOrderHeadersDTO> erpOrdersList = new ArrayList<SFDCOrderHeadersDTO>();
        if (erpOrderStatusHeaderResponseEntity.hasBody()) {
            ErpOrderStatusDTO erpOrderStatusDTO = erpOrderStatusHeaderResponseEntity.getBody();
            erpOrdersList.addAll(erpOrderStatusDTO.getOrdersList());

            totalCount = decrementTotalCount(erpOrderStatusDTO.getTotalCount(), erpOrderStatusDTO.getOrdersList().size());

            // Loop until there are more order statuses available..
            while (totalCount > 0) {
                ResponseEntity<ErpOrderStatusDTO> nextErpOrderStatusResponseEntity =
                        erpOrderStatusRequest.fetchOrderStatus(new PageNumberDTO(++pageNumber));
                if (nextErpOrderStatusResponseEntity.hasBody()) {
                    ErpOrderStatusDTO nextErpOrderStatusDTO = nextErpOrderStatusResponseEntity.getBody();
                    erpOrdersList.addAll(nextErpOrderStatusDTO.getOrdersList());
                    totalCount = decrementTotalCount(totalCount, nextErpOrderStatusDTO.getOrdersList().size());
                }
            }
        }
        return erpOrdersList;
    }

    private long decrementTotalCount(long totalCount, long nextSize) {
        return (totalCount - nextSize);
    }
}
