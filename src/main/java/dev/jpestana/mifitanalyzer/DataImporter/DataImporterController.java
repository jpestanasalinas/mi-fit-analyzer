package dev.jpestana.mifitanalyzer.DataImporter;

import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Services.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/import")
public class DataImporterController {

    public static final String INVALID_FILE_MESSAGE = "Please upload a csv file!";
    public static final String COULD_NOT_PROCESS_FILE_MESSAGE = "Could not save the data";

    private final CSVService activityCSVService;

    private final CSVService activityMinuteCSVService;

    private final CSVService activityStageCSVService;

    private final CSVService bodyCSVService;

    private final CSVService heartrateCSVService;

    @Autowired
    public DataImporterController(@Qualifier("activityCSVService") CSVService activityCSVService,
                                  @Qualifier("activityMinuteCSVService") CSVService activityMinuteCSVService,
                                  @Qualifier("activityStageCSVService") CSVService activityStageCSVService,
                                  @Qualifier("bodyCSVService") CSVService bodyCSVService,
                                  @Qualifier("heartrateCSVService") CSVService heartrateCSVService) {
        this.activityCSVService = activityCSVService;
        this.activityMinuteCSVService = activityMinuteCSVService;
        this.activityStageCSVService = activityStageCSVService;
        this.bodyCSVService = bodyCSVService;
        this.heartrateCSVService = heartrateCSVService;
    }

    @PostMapping ("/activity-data")
    public ResponseEntity<String> getActivityDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {

        activityCSVService.save(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(OK)
                .body(message);

    }

    @PostMapping("/activity-minute-data")
    public ResponseEntity<String> getActivityMinuteDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {

        activityMinuteCSVService.save(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(OK)
                .body(message);
    }

    @PostMapping("/activity-stage-data")
    public ResponseEntity<String> getActivityStageDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {

        activityStageCSVService.save(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(OK)
                .body(message);
    }

    @PostMapping("/body-data")
    public ResponseEntity<String> getBodyDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {

        bodyCSVService.save(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(OK)
                .body(message);
    }

    @PostMapping("/heartrate-data")
    public ResponseEntity<String> getHeartrateDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {

        heartrateCSVService.save(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(OK)
                .body(message);
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<String> handleInvalidFileType(InvalidFileTypeException e) {
        return ResponseEntity.status(BAD_REQUEST).body(INVALID_FILE_MESSAGE);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOError(IOException e) {
        return ResponseEntity.status(EXPECTATION_FAILED).body(COULD_NOT_PROCESS_FILE_MESSAGE);
    }
}
