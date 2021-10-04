package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TCPAssetInfoDTO implements Serializable, Comparable<TCPAssetInfoDTO> {
    private static final long serialVersionUID = 123222321884L;

    private String id;
    private String name;
    private String thermaxName;
    private String accountId;
    private String salesOrderNumber;
    @Temporal(TemporalType.DATE)
    private Date warrantyExpiredDate;
    @Temporal(TemporalType.DATE)
    private Date installationDate;
    private Boolean recommendations;
    private String topMostSpareName;
    private Integer spareCount;
    private String topMostServiceName;
    private Integer serviceCount;
    private String ownerId;
    private String ownerName;
    private String ownerUserRoleName;
    private String ownerCountryCode;
    private String ownerPhoneNumber;
    @CreationTimestamp
    private ZonedDateTime createdDate;
    @CreationTimestamp
    private ZonedDateTime lastModifiedDate;
   /* @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "assetTh")
    private List<SFDCRecommendationsDTO> recommendationsTh;*/
    @Transient
    @JsonIgnore
    private String statusOB;
    @Override
    public int compareTo(TCPAssetInfoDTO asset) {
        return Boolean.compare(asset.getRecommendations(), recommendations);
    }
}
