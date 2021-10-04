package com.thermax.cp.salesforce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentLinkEntity {

    private String ContentDocumentId;
    private String linkedEntityId;
    private String visibility;
}
