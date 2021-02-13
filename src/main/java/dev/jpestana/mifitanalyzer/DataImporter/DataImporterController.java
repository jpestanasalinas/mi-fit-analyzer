package dev.jpestana.mifitanalyzer.DataImporter;

import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Services.ActivityCSVService;
import dev.jpestana.mifitanalyzer.DataImporter.Services.ActivityMinuteCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/import")
public class DataImporterController {

    @Autowired
    private ActivityCSVService activityCSVService;

    @Autowired
    private ActivityMinuteCSVService activityMinuteCSVService;

    @PostMapping ("/activity-data")
    public ResponseEntity<ResponseMessage> getActivityDataFromCSV(@RequestParam("file") MultipartFile file) {
        String message;

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                activityCSVService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/csv/download/")
                        .path(file.getOriginalFilename())
                        .toUriString();

                return ResponseEntity.status(OK).body(new ResponseMessage(message,fileDownloadUri));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(EXPECTATION_FAILED).body(new ResponseMessage(message,""));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(BAD_REQUEST).body(new ResponseMessage(message,""));
    }

    @PostMapping("/activity-minute-data")
    public ResponseEntity<String> getActivityMinuteDataFromCSV(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            activityMinuteCSVService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(OK)
                    .body(message);
        } catch (IOException e) {
            message = "Could not save the data";
            return ResponseEntity.status(EXPECTATION_FAILED).body(message);
        } catch (InvalidFileTypeException e) {
            message = "Please upload a csv file!";
            return ResponseEntity.status(BAD_REQUEST).body(message);
        }
    }
}
