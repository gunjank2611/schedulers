package com.thermax.cp.salesforce.dto.contacts;

import lombok.Data;

import java.util.List;

@Data
public class SFDCContactsListDTO {
    private String totalSize;
    private String done;
    private List<SFDCContactsDTO> records;
}
