package com.thermax.cp.salesforce.utils;

import com.thermax.cp.salesforce.dto.upload.FileUploadResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
public class CSVFileUpload {


    public static void uploadFileForUser(File file,
                                         String userName) throws IOException, InterruptedException, ExecutionException {

        Map<String, Object> asyncUploadResponseMap = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("file contents"+line);
        }

        // Note ::
        // a) Entity Id is also required, to link upload record in SFDC.
        // b) Username is required so that files are uploaded in user-specific folder(s).

       // if (fileUploadServiceUtils.isValid(multipartFile)) {

            // 1. Fetching necessary information from the file..
            String originalFileName = file.getName();
            String title = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

            // 2. Adding timestamp to the filename..
            String fileName = title + "_" + timeStamp + extension;
            long fileSize = file.getTotalSpace();
            String contentType = file.getCanonicalPath();

            // 3. Putting File related information into the console..
            log.info("Inside uploadFileForUser of FileUploadController..");
            log.info("File Name : " + fileName);
            log.info("File Size : " + fileSize);
            log.info("Content Type : " + contentType);

            // 4. Now, from here, first make an asynchronous call to Azure..
          //  CompletableFuture<FileUploadResponseDTO> uploadToAzure = asyncFileUploaderService.uploadFileToAzure(userName, fileName, multipartFile.getInputStream(), fileSize);

            // 5. Wait until both are completed..
           // CompletableFuture.allOf(uploadToAzure).join();

            // 6. Consolidate & build responses from the request(s)..
            //asyncUploadResponseMap = fileUploadServiceUtils.buildFileUploadResponseMapForUser(uploadToAzure);

       /* } else {
            // Scenario : User can even, create a profile without photo.
            log.info("No Image provided, Skipping fileUpload for user..");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }*/
      //  return new ResponseEntity<>(asyncUploadResponseMap, HttpStatus.OK);
    }
}
