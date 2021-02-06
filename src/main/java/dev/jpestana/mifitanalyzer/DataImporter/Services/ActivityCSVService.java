package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.CSVHelper;
import dev.jpestana.mifitanalyzer.DataImporter.Entities.Activity;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ActivityCSVService {

    private ActivityRepository repository;

    @Autowired
    public ActivityCSVService(ActivityRepository repository) {
        this.repository = repository;
    }

    public void save(MultipartFile file) {
        try {
            List<Activity> activities = CSVHelper.csvToActivities(file.getInputStream());
            repository.saveAll(activities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<Activity> tutorials = repository.findAll();

        return CSVHelper.acctivvitiesToCSV(tutorials);
    }

    public List<Activity> getAllTutorials() {
        return repository.findAll();
    }
}
