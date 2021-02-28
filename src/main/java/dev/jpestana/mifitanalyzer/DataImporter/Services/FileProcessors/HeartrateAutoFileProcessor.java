package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.HeartrateAuto;
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
public class HeartrateAutoFileProcessor extends FileProcessor<HeartrateAuto> {

    public HeartrateAutoFileProcessor() {
        super();
    }

    @Override
    protected List<HeartrateAuto> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<HeartrateAuto> heartrates = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for(CSVRecord csvRecord: csvRecords) {
            HeartrateAuto heartrateAuto = new HeartrateAuto(
                    Date.valueOf(csvRecord.get("date")),
                    Timestamp.valueOf(csvRecord.get("time")),
                    Integer.valueOf(csvRecord.get("heartRate"))
                    );

            heartrates.add(heartrateAuto);
        }

        return heartrates;
    }

}
