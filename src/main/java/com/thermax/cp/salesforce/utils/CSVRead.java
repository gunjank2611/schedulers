package com.thermax.cp.salesforce.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class CSVRead {
    public static List<? extends Object> readDump(String url, String headerLine, Object dtoClass) throws IOException {
        String fileUrl = url;
        URL rowdata = new URL(fileUrl);
        URLConnection data = rowdata.openConnection();
        ICsvBeanReader beanReader = null;
        List<Object> list = new ArrayList<>();
        Reader fr = null;
        try {
            log.info("Started processing file: {}", data.getURL());
            fr = new InputStreamReader(data.getInputStream());
            beanReader = new CsvBeanReader(fr, CsvPreference.STANDARD_PREFERENCE);
            final String[] header = beanReader.getHeader(true);
            if (Validations.validateCSVHeaders(header, headerLine)) {
                log.info("Headers a valid for file");
                Object dumpDto;
                while ((dumpDto = beanReader.read(dtoClass.getClass(), header)) != null) {
                    log.debug("lineNo: {}, rowNo: {}, customer: {}", beanReader.getLineNumber(),
                            beanReader.getRowNumber(), dumpDto);
                    if (dumpDto != null) {
                        list.add(dumpDto);
                    }
                }
            }
            log.info("Total Records Processed: {}", list.size());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        return null;
    }
}
