package com.thermax.cp.salesforce.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseBean {

    private Date date;
    private String message;
    private String requestDescription;
}
