package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.product.ProductListDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

@StepScope
public class ProductItemReader implements ItemReader<SFDCProductInfoDTO> {
   private   String query;
   @Autowired
   private  SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
   @Autowired
   private SfdcServiceUtils sfdcServiceUtils;
   private List<SFDCProductInfoDTO> sfdcProductInfoDTOList;
   private int nextProductIndex;
   private String frequency;

   public ProductItemReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
   {
    this.query= QueryConstants.PRODUCT_DETAILS_QUERY;
    this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
    this.nextProductIndex=0;
    this.frequency=frequency;
   }
    @Override
    public SFDCProductInfoDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            sfdcProductInfoDTOList=getProductDetails(query,frequency);
        }
        SFDCProductInfoDTO nextProduct;
        if (nextProductIndex < sfdcProductInfoDTOList.size()) {
            nextProduct = sfdcProductInfoDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            nextProduct = null;
        }

        return nextProduct;
    }

    private boolean productDataNotInitialized()
    {
       return this.sfdcProductInfoDTOList==null;
    }

    private List<SFDCProductInfoDTO> getProductDetails(String query,String date) throws UnsupportedEncodingException {
        String productDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<ProductListDTO> productdetailsList = sfdcBatchDataDetailsRequest.loadProductDetails(productDetailsQuery);
        if (productdetailsList != null) {
            return productdetailsList.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Product Details from SFDC for the specified date : " + date);
        }
    }
}
