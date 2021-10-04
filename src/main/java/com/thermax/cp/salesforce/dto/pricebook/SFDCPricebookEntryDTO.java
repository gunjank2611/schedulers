package com.thermax.cp.salesforce.dto.pricebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;

public class SFDCPricebookEntryDTO {

        public AttributesDTO attributes;
        @JsonProperty("CurrencyIsoCode")
        public String currencyIsoCode;
        @JsonProperty("Id")
        public String id;
        @JsonProperty("IsActive")
        public boolean isActive;
        @JsonProperty("LastModifiedDate")
        public String lastModifiedDate;
        @JsonProperty("Name")
        public String name;
        @JsonProperty("Pricebook2Id")
        public String pricebook2Id;
        @JsonProperty("Product2Id")
        public String product2Id;
        @JsonProperty("ProductCode")
        public String productCode;
        @JsonProperty("SystemModstamp")
        public String systemModstamp;
        @JsonProperty("UnitPrice")
        public double unitPrice;
        @JsonProperty("UseStandardPrice")
        public boolean useStandardPrice;
    }

