package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Sport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class SportFileProcessor extends FileProcessor<Sport> {

    public SportFileProcessor() {
        super();
    }

    @Override
    protected List<Sport> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<Sport> sports = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for(CSVRecord csvRecord: csvRecords) {
            Sport sport = new Sport(
                    Integer.valueOf(csvRecord.get("type")),
                    Timestamp.valueOf(csvRecord.get("startTime")),
                    Integer.valueOf(csvRecord.get("sportTime")),
                    Integer.valueOf(csvRecord.get("distance")),
                    Integer.valueOf(csvRecord.get("maxpace")),
                    Integer.valueOf(csvRecord.get("minPace")),
                    Integer.valueOf(csvRecord.get("avgPace")),
                    Float.valueOf(csvRecord.get("calories"))
                    );

            sports.add(sport);
        }

        return sports;
    }

}
