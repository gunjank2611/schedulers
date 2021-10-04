package com.thermax.cp.salesforce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    private String title;
    private String pathOnClient;
    private String contentLocation;
    private String versionData;
}
