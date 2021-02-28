package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Sleep;
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
public class SleepFileProcessor extends FileProcessor<Sleep> {

    public SleepFileProcessor() {
        super();
    }

    @Override
    protected List<Sleep> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<Sleep> sleeps = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for(CSVRecord csvRecord: csvRecords) {
            Sleep sleep = new Sleep(
                    Date.valueOf(csvRecord.get("date")),
                    Timestamp.valueOf(csvRecord.get("lastSyncTime")),
                    Integer.valueOf(csvRecord.get("deepSleepTime")),
                    Integer.valueOf(csvRecord.get("shallowSleepTime")),
                    Integer.valueOf(csvRecord.get("wakeTime")),
                    Timestamp.valueOf(csvRecord.get("start")),
                    Timestamp.valueOf(csvRecord.get("stop"))
                    );

            sleeps.add(sleep);
        }

        return sleeps;
    }

}
