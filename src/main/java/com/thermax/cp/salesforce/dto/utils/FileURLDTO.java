package com.thermax.cp.salesforce.dto.utils;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class FileURLDTO {
    private String fileUrl;
    private String endPoint;
    private ZonedDateTime fileUploadTimeStamp;
}
