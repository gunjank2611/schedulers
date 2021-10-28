package com.thermax.cp.salesforce.dto.users;

import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import lombok.Data;

import java.util.List;

@Data
public class SFDCUserDTOList {
    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCUsersDTO> records;
}
