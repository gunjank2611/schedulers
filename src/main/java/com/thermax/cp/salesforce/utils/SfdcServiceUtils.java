package com.thermax.cp.salesforce.utils;

import com.thermax.cp.salesforce.dto.upload.ContentDocumentListDTO;
import com.thermax.cp.salesforce.feign.request.SfdcContentDocumentRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class SfdcServiceUtils {

    public String decodeRequestQuery(String query) throws UnsupportedEncodingException {
        return URLDecoder.decode(query, "UTF-8");
    }

    public ResponseEntity<ContentDocumentListDTO> getContentDocumentForContentVersionId(SfdcContentDocumentRequest sfdcContentDocumentRequest, String contentVersionId) throws UnsupportedEncodingException {
        String contentDocumentQuery = decodeRequestQuery(QueryConstants.FETCH_CONTENT_DOCUMENT_QUERY) + "'" + contentVersionId + "'";
        ResponseEntity<ContentDocumentListDTO> contentDocumentResponse = sfdcContentDocumentRequest.getContentDocument(contentDocumentQuery);
        return contentDocumentResponse;
    }

}
