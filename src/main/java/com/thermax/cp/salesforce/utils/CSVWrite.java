package com.thermax.cp.salesforce.utils;

import com.thermax.cp.salesforce.dto.utils.UploadResponseDTO;
import com.thermax.cp.salesforce.feign.request.FileUploadFeignClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CSVWrite {

    @Autowired
    private FileUploadFeignClient fileUploadFeignClient;


    private static final Logger LOGGER = LogManager.getLogger(CSVWrite.class);

    public CompletableFuture<String> writeToCSV(List<? extends Object> dtoList, String[] headers, String fileName, String apiName) throws IOException {
        String csvOutput = null;
        ICsvBeanWriter beanWriter = null;
        File output = new File(fileName);
        if (output.exists()) {
            output.delete();
        }
        output.createNewFile();
        OutputStream outputStream = new FileOutputStream(output);
        try (BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {

            beanWriter = new CsvBeanWriter(csvWriter, CsvPreference.STANDARD_PREFERENCE);


            beanWriter.writeHeader(headers);
            for (final Object dto : dtoList) {
                beanWriter.write(dto, headers);
            }

            beanWriter.flush();

            ResponseEntity<UploadResponseDTO> responseDTO = fileUploadFeignClient.uploadFileToAzure(apiName + "_" + Instant.now().toEpochMilli(), output);
            if (responseDTO.getStatusCode() == HttpStatus.OK && responseDTO.getBody() != null) {
                csvOutput = responseDTO.getBody().getAzureBlobUrl();
                LOGGER.info("Output file is saved at azure blob location" + csvOutput);

            }

        } catch (Exception e) {
            LOGGER.error("Getting exception while executing batchOperationForStagingData method .", e);
        } finally {
            beanWriter.close();
            outputStream.close();
            if (output.exists()) {
                output.delete();
            }
        }
        return CompletableFuture.completedFuture(csvOutput);
    }


}
