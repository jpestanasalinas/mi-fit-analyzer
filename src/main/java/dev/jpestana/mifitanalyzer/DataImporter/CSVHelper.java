package dev.jpestana.mifitanalyzer.DataImporter;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Activity;
import org.apache.commons.csv.*;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel");
    }

    public static List<Activity> csvToActivities(InputStream is) {
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
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream acctivvitiesToCSV(List<Activity> activities) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (Activity activity : activities) {
                List<String> data = Arrays.asList(
                        String.valueOf(activity.getId()),
                        String.valueOf(activity.getDate()),
                        String.valueOf(activity.getLastSyncTime()),
                        String.valueOf(activity.getSteps()),
                        String.valueOf(activity.getDistance()),
                        String.valueOf(activity.getRunDistance()),
                        String.valueOf(activity.getCalories())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}
