package com.thermax.cp.salesforce.mapper;

import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.recommendations.TCPRecommendationsDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RecommendationsMapper {

    TCPRecommendationsDTO convertToTCPRecommendationDTO(final SFDCRecommendationsDTO sfdcRecommendationsDTO);


}
