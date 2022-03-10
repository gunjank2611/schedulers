package com.thermax.cp.salesforce.utils;

import com.thermax.cp.salesforce.dto.orders.PageNumberDTO;
import com.thermax.cp.salesforce.exception.InvalidInputException;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
public class Validations {

    public void validateOrdersInputFields(PageNumberDTO request) {
  /*      String division = request.getDivision();
        String region = request.getRegion();*/
        if (StringUtils.isEmpty(request.getPageNumber() + "")) {
            throw new InvalidInputException("Please send a valid pageNumber");
        }
       /* else if(!EnumUtils.isValidEnum(EnumRegion.class, region)
                && !request.getRegion().equalsIgnoreCase("empty")) {
            throw new InvalidInputException("Please send a valid region!");
        } else if(StringUtils.isEmpty(request.getSerialOcNumber())) {
            throw new InvalidInputException("Please send a valid Serial/OC number!");
        } else if(StringUtils.isEmpty(request.getAccountId())) {
            throw new InvalidInputException("Please send a valid account Id!");
        } else if(StringUtils.isEmpty(request.getAccountName())) {
            throw new InvalidInputException("Please send a valid account name!");
        }*/
    }

    public static boolean validateCSVHeaders(String[] headerValues, String headerLine) {
        String[] columns = split(headerLine, "\\|");
        for (int i = 0; i < columns.length; i++) {
            if (!headerValues[i].equalsIgnoreCase(columns[i].trim())) {
                return false;
            }
        }
        return true;
    }

    public static String[] split(String str, String strSeparator) {
        if (str == null || strSeparator == null) {
            return null;
        }
        StringTokenizer tokenizer = new StringTokenizer(str, strSeparator);
        String[] strArrValues = new String[tokenizer.countTokens()];
        int count = 0;
        while (tokenizer.hasMoreTokens()) {
            strArrValues[count++] = tokenizer.nextToken().trim();
        }
        return strArrValues;
    }
}
