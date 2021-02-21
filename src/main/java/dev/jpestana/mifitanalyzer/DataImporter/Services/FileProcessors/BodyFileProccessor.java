package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Body;
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
public class BodyFileProccessor extends FileProcessor<Body> {

    public BodyFileProccessor() {
        super();
    }

    @Override
    protected List<Body> map(BufferedReader buffReadr) throws IOException {
        CSVParser csvParser = new CSVParser(buffReadr,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<Body> bodies = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for(CSVRecord csvRecord: csvRecords) {
            Body body = new Body(
                    Timestamp.valueOf(csvRecord.get("timestamp")),
                    Float.valueOf(csvRecord.get("weight")),
                    Float.valueOf(csvRecord.get("height")),
                    Integer.valueOf(csvRecord.get("bmi")),
                    Float.valueOf(csvRecord.get("fatRate")),
                    Float.valueOf(csvRecord.get("bodyWaterRate")),
                    Float.valueOf(csvRecord.get("boneMass")),
                    Float.valueOf(csvRecord.get("metabolism")),
                    Float.valueOf(csvRecord.get("muscleRate")),
                    Float.valueOf(csvRecord.get("visceralFat")),
                    Float.valueOf(csvRecord.get("impedance"))
            );

            bodies.add(body);
        }

        return bodies;
    }

}
