package com.thermax.cp.salesforce.advice;

import com.thermax.cp.salesforce.exception.*;
import com.thermax.cp.salesforce.exception.response.ExceptionResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class SfdcExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponseBean exceptionResponseBean = buildExceptionResponse(ex, request);
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionResponseBean, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OAuthResponseException.class)
    public ResponseEntity<Object> handleOAuthResponseException(Exception ex, WebRequest request) {
        ExceptionResponseBean exceptionResponseBean = buildExceptionResponse(ex, request);
        return new ResponseEntity<>(exceptionResponseBean, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountInfoNotFoundException.class)
    public ResponseEntity<Object> handleAccountInfoNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponseBean exceptionResponseBean = buildExceptionResponse(ex, request);
        return new ResponseEntity<>(exceptionResponseBean, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssetDetailsNotFoundException.class)
    public ResponseEntity<Object> handleAssetDetailsNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponseBean exceptionResponseBean = buildExceptionResponse(ex, request);
        return new ResponseEntity<>(exceptionResponseBean, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssetsNotFoundForAccountException.class)
    public ResponseEntity<Object> handleAssetNotFoundForAccountException(Exception ex, WebRequest request) {
        ExceptionResponseBean exceptionResponseBean = buildExceptionResponse(ex, request);
        return new ResponseEntity<>(exceptionResponseBean, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileEntityUploadException.class)
    public ResponseEntity<Object> handleFileEntityUploadException(Exception ex, WebRequest request) {
        ExceptionResponseBean exceptionResponseBean = buildExceptionResponse(ex, request);
        return new ResponseEntity<>(exceptionResponseBean, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method will help in building the exceptionResponseObjects for either of aforementioned exception categories.
     *
     * @param ex
     * @param request
     * @return ExceptionResponseBean
     */
    private ExceptionResponseBean buildExceptionResponse(Exception ex, WebRequest request) {
        return new ExceptionResponseBean(new Date(), ex.getMessage(), request.getDescription(false));
    }
}
