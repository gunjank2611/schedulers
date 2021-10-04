package com.thermax.cp.salesforce.utils;

import com.thermax.cp.salesforce.dto.recommendations.TCPRecommendationsDTO;
import com.thermax.cp.salesforce.dto.utils.UploadResponseDTO;
import com.thermax.cp.salesforce.feign.request.FileUploadFeignClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
@Component
public class CSVWrite {

    @Autowired
    private FileUploadFeignClient fileUploadFeignClient;

    private static final Logger LOGGER = LogManager.getLogger(CSVWrite.class);

    public CompletableFuture<String> writeToCSV(List<TCPRecommendationsDTO> tcpRecommendationsDTOList) throws IOException {
        String csvOutput = null;
        ICsvBeanWriter beanWriter = null;
        File output  = new File("recommendations.csv");
        if (output.exists()) {
            output.delete();
        }
        output.createNewFile();
        OutputStream outputStream = new FileOutputStream(output);
        try (BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {

            beanWriter = new CsvBeanWriter(csvWriter, CsvPreference.STANDARD_PREFERENCE);

            final String[] header = new String[]{"id", "asset", "accountName", "plannedShutdownDescription", "plannedShutdownDate",
                    "service", "spare", "selectedServices", "selectedSpares","recommendationType","recommendationSubType","createdDate","lastModifiedDate"};

            beanWriter.writeHeader(header);
            for (final TCPRecommendationsDTO dto : tcpRecommendationsDTOList) {
                beanWriter.write(dto, header);
            }
            byte[] bytes = csvWriter.toString().substring(30,csvWriter.toString().length()).getBytes("UTF-8");
            outputStream.write(bytes);
            beanWriter.flush();

            ResponseEntity<UploadResponseDTO> responseDTO = fileUploadFeignClient.uploadFileToAzure( "recommendations_" + String.valueOf(Instant.now().toEpochMilli()),output);
            if (responseDTO.getStatusCode() == HttpStatus.OK) {
                csvOutput = responseDTO.getBody().getAzureBlobUrl();
                                LOGGER.info("Output file is saved at azure blob location" + csvOutput);

            }

        } catch (Exception e) {
            LOGGER.error("Getting exception while executing batchOperationForStagingData method .", e);
        }
        finally
        {
            beanWriter.close();
            outputStream.close();
        }
        return CompletableFuture.completedFuture(csvOutput);
    }

    public MultipartFile fileToMultipartFile(File file) {
        FileItem fileItem = createFileItem(file);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    private static FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}
