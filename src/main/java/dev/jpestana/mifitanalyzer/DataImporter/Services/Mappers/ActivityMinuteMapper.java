package dev.jpestana.mifitanalyzer.DataImporter.Services.Mappers;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ActivityMinuteMapper {

    public List<ActivityMinute> csvToActivities(InputStream is) {
        try(BufferedInputStream buffIs = new BufferedInputStream(is);
            BOMInputStream bomIn = new BOMInputStream(buffIs)) {

            final boolean hasBOM = bomIn.hasBOM();

            final BufferedReader buffReadr = new BufferedReader(
                    new InputStreamReader(hasBOM ? bomIn : buffIs, StandardCharsets.UTF_8));
            if (!hasBOM) {
                buffIs.reset();
            }

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
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
