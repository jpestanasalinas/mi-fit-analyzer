package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Heartrate;
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
public class HeartrateFileProccessor extends FileProcessor<Heartrate> {

    public HeartrateFileProccessor() {
        super();
    }

    @Override
    protected List<Heartrate> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<Heartrate> heartrates = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for(CSVRecord csvRecord: csvRecords) {
            Heartrate heartrate = new Heartrate(
                    Date.valueOf(csvRecord.get("date")),
                    Timestamp.valueOf(csvRecord.get("lastSyncTime")),
                    Integer.valueOf(csvRecord.get("heartRate")),
                    Timestamp.valueOf(csvRecord.get("timestamp"))
                    );

            heartrates.add(heartrate);
        }

        return heartrates;
    }

}
