package com.thermax.cp.salesforce.mapper;

import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.recommendations.TCPRecommendationsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecommendationsMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "accountName", target = "accountName")
    @Mapping(source = "asset", target = "asset")
    @Mapping(source = "plannedShutdownDate", target = "plannedShutdownDate")
    @Mapping(source = "creationDate", target = "createdDate")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    @Mapping(source = "spareLifeCycleDays", target = "lifeCycleDays")
    @Mapping(source = "selectedServices", target = "selectedServices")
    @Mapping(source = "selectedSpares", target = "selectedSpares")
    @Mapping(source = "spare", target = "spare")
    @Mapping(source = "service", target = "service")
    @Mapping(source = "recommendationType", target = "recommendationType")
    @Mapping(source = "recommendationSubType", target = "recommendationSubType")

    TCPRecommendationsDTO convertToTCPRecommendationDTO(final SFDCRecommendationsDTO sfdcRecommendationsDTO);


}
