package com.thermax.cp.salesforce.dto.asset;

import com.thermax.cp.salesforce.dto.orders.SFDCOpportunityContactRoleDTO;
import lombok.Data;

import java.util.List;

@Data
public class SFDCOpportunityContactRoleDTOList {
    private String totalSize;
    private String done;
    private List<SFDCOpportunityContactRoleDTO> records;
    private String nextRecordsUrl;
}
