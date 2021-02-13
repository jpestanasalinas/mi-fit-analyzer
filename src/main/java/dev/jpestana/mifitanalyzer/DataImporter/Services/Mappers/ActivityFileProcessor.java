package dev.jpestana.mifitanalyzer.DataImporter.Services.Mappers;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Activity;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityFileProcessor {
    public static String TYPE = "text/csv";

    public ActivityFileProcessor() {
    }

    public List<Activity> csvToActivities(MultipartFile file) throws IOException, InvalidFileTypeException {

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

    private List<Activity> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<Activity> activities = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            Activity activity = new Activity(
                    Date.valueOf(csvRecord.get("date")),
                    Date.valueOf(csvRecord.get("lastSyncTime")),
                    Integer.parseInt(csvRecord.get("steps")),
                    Float.parseFloat(csvRecord.get("distance")),
                    Float.parseFloat(csvRecord.get("runDistance")),
                    Float.parseFloat(csvRecord.get("calories"))
            );

            activities.add(activity);
        }

        return activities;
    }

    private boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
