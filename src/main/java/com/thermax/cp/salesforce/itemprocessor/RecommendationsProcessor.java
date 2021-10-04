package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCServiceDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCSpareDTO;
import org.springframework.batch.item.ItemProcessor;

public class RecommendationsProcessor  implements ItemProcessor<SFDCRecommendationsDTO, SFDCRecommendationsDTO>  {

    @Override
    public SFDCRecommendationsDTO process(SFDCRecommendationsDTO sfdcRecommendationsDTO){
        return sfdcRecommendationsDTO;
    }
}
