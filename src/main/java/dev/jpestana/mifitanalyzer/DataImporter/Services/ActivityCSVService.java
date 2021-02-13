package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Activity;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.ActivityFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ActivityCSVService {

    private ActivityRepository repository;

    private final ActivityFileProcessor fileProcessor;

    @Autowired
    public ActivityCSVService(ActivityRepository repository, ActivityFileProcessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    public void save(MultipartFile file) throws IOException, InvalidFileTypeException {
            List<Activity> activities = fileProcessor.parseCSV(file);
            repository.saveAll(activities);
    }

}
