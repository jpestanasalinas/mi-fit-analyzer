package dev.jpestana.mifitanalyzer.DataImporter;

import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Services.ActivityCSVService;
import dev.jpestana.mifitanalyzer.DataImporter.Services.ActivityMinuteCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/import")
public class DataImporterController {

    private static final String INVALID_FILE_MESSAGE = "Please upload a csv file!";
    private static final String COULD_NOT_PROCESS_FILE_MESSAGE = "Could not save the data";

    @Autowired
    private ActivityCSVService activityCSVService;

    @Autowired
    private ActivityMinuteCSVService activityMinuteCSVService;

    @PostMapping ("/activity-data")
    public ResponseEntity<String> getActivityDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {

        checkFileExists(file);

        activityCSVService.save(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(OK)
                .body(message);

    }

    @PostMapping("/activity-minute-data")
    public ResponseEntity<String> getActivityMinuteDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {
        String message;

        checkFileExists(file);

        activityMinuteCSVService.save(file);
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(OK)
                .body(message);
    }

    private void checkFileExists(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            throw new InvalidFileTypeException("file not exists");
        }
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<String> handleInvalidFileType(InvalidFileTypeException e) {
        return ResponseEntity.status(BAD_REQUEST).body(INVALID_FILE_MESSAGE);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOError(IOException e) {
        return ResponseEntity.status(EXPECTATION_FAILED).body(INVALID_FILE_MESSAGE);
    }
}
