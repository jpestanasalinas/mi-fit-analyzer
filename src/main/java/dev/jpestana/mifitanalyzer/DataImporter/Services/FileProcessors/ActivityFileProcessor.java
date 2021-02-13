package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Activity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityFileProcessor extends FileProcessor<Activity> {
    public static String TYPE = "text/csv";

    public ActivityFileProcessor() {
        super();
    }

    protected List<Activity> map(BufferedReader buffReadr) throws IOException {
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
}
