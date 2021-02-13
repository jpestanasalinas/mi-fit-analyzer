package dev.jpestana.mifitanalyzer.DataImporter.Services.Mappers;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ActivityMinuteFileProcessor {

    public static String TYPE = "text/csv";

    public ActivityMinuteFileProcessor() {
    }

    public List<ActivityMinute> csvToActivityMinutes(MultipartFile file) throws IOException, InvalidFileTypeException {

        if(!hasCSVFormat(file)) {
            throw new InvalidFileTypeException("file type is not compatible, only CSV accepted");
        }

        InputStream is = file.getInputStream();
        try(BufferedInputStream buffIs = new BufferedInputStream(is);
            BOMInputStream bomIn = new BOMInputStream(buffIs)) {

            final BufferedReader buffReadr = chooseBufferedReader(buffIs, bomIn);
            return map(buffReadr);

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private BufferedReader chooseBufferedReader(BufferedInputStream buffIs, BOMInputStream bomIn) throws IOException {
        final boolean hasBOM = bomIn.hasBOM();
        final BufferedReader buffReadr = new BufferedReader(
                new InputStreamReader(hasBOM ? bomIn : buffIs, StandardCharsets.UTF_8));
        if (!hasBOM) {
            buffIs.reset();
        }
        return buffReadr;
    }

    private List<ActivityMinute> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<ActivityMinute> activityMinutes = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            ActivityMinute activityMinute = new ActivityMinute(
                    Date.valueOf(csvRecord.get("date")),
                    Timestamp.valueOf(csvRecord.get("time")),
                    Integer.parseInt(csvRecord.get("steps"))
            );

            activityMinutes.add(activityMinute);
        }

        return activityMinutes;
    }

    private boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
