package com.thermax.cp.salesforce.mapper;

import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.recommendations.TCPRecommendationsDTO;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2021-09-22T14:44:11+0530",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class RecommendationsMapperImpl implements RecommendationsMapper{


    @Override
    public TCPRecommendationsDTO convertToTCPRecommendationDTO(SFDCRecommendationsDTO sfdcRecommendationsDTO) {
        if(sfdcRecommendationsDTO==null)
        {
            return  null;
        }

        TCPRecommendationsDTO tcpRecommendationsDTO=new TCPRecommendationsDTO();
        tcpRecommendationsDTO.setRecommendationType(sfdcRecommendationsDTO.getRecommendationType());
        tcpRecommendationsDTO.setRecommendationSubType(sfdcRecommendationsDTO.getRecommendationSubType());
        tcpRecommendationsDTO.setAsset(sfdcRecommendationsDTO.getAsset());
        tcpRecommendationsDTO.setAccountName(sfdcRecommendationsDTO.getAccountName());
        tcpRecommendationsDTO.setLastModifiedDate(sfdcRecommendationsDTO.getLastModifiedDate());
        tcpRecommendationsDTO.setId(sfdcRecommendationsDTO.getId());
        tcpRecommendationsDTO.setPlannedShutdownDate(sfdcRecommendationsDTO.getPlannedShutDownDate());
        tcpRecommendationsDTO.setSelectedServices(sfdcRecommendationsDTO.getSelectedServices());
        tcpRecommendationsDTO.setSelectedSpares(sfdcRecommendationsDTO.getSelectedSpares());
        tcpRecommendationsDTO.setSpare(sfdcRecommendationsDTO.getSpare());
        tcpRecommendationsDTO.setService(sfdcRecommendationsDTO.getService());

        return tcpRecommendationsDTO;
    }
}
