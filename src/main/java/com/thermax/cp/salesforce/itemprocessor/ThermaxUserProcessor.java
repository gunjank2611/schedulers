package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.asset.*;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.users.SFDCUserRoleDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersDTO;
import org.springframework.batch.item.ItemProcessor;

public class ThermaxUserProcessor implements ItemProcessor<ThermaxUsersDTO, ThermaxUsersDTO> {

    @Override
    public ThermaxUsersDTO process(ThermaxUsersDTO thermaxUsersDTO){
        if(thermaxUsersDTO!=null)
        {
            SFDCUserRoleDTO userRoleDTO=thermaxUsersDTO.getUserRole();
            if(userRoleDTO!=null) {
                thermaxUsersDTO.setUserRoleName(userRoleDTO.getName());
            }
        }

        return thermaxUsersDTO;
    }
}

