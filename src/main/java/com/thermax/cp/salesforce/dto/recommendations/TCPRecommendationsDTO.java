package com.thermax.cp.salesforce.dto.recommendations;

import lombok.*;

@ToString
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TCPRecommendationsDTO {
    private static final long serialVersionUID = 123822342884L;
    private String id;
    private String asset;
    private String accountName;
    private String plannedShutdownDescription;
    private String plannedShutdownDate;
    private String service;
    private String spare;
    private String status;
    private String selectedServices;
    private String selectedSpares;
    private String recommendationType;
    private String recommendationSubType;
    private String createdDate;
    private String lastModifiedDate;
}
