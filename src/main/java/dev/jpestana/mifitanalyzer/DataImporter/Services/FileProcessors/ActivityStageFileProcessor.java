package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityStage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityStageFileProcessor extends FileProcessor<ActivityStage> {

    public ActivityStageFileProcessor() {
        super();
    }

    @Override
    protected List<ActivityStage> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<ActivityStage> activityStages = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for(CSVRecord csvRecord: csvRecords) {
            ActivityStage activityStage = new ActivityStage(
                    Date.valueOf(csvRecord.get("date")),
                    Timestamp.valueOf(csvRecord.get("start")),
                    Timestamp.valueOf(csvRecord.get("stop")),
                    Float.valueOf(csvRecord.get("distance")),
                    Float.valueOf(csvRecord.get("calories")),
                    Integer.valueOf(csvRecord.get("steps"))
            );

            activityStages.add(activityStage);
        }

        return activityStages;
    }
}
