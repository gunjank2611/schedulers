package com.thermax.cp.salesforce.dto.upload;

import lombok.Data;

import java.util.List;

@Data
public class LinkedContentDocumentToEntityDTO {
    public String id;
    public boolean success;
    public List<Object> errors;
}
