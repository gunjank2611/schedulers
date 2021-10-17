package com.thermax.cp.salesforce.dto.users;

import lombok.Data;

import java.util.List;

@Data
public class ThermaxUsersListDTO {
    private String totalSize;
    private String done;
    private List<ThermaxUsersDTO> records;
}
