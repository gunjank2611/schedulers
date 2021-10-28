package com.thermax.cp.salesforce.dto.opportunity;

import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

import java.util.List;

@Data
public class SFDCOpportunityDTOList {

  private String totalSize;
  private String done;
  private List<SFDCOpportunityDTO> records;
  private String nextRecordsUrl;
}
