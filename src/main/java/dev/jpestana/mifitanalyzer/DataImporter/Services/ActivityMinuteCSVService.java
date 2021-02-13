package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityMinuteRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.Mappers.ActivityMinuteFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ActivityMinuteCSVService implements CSVService {

    private final ActivityMinuteRepository repository;

    private final ActivityMinuteFileProcessor fileProcessor;

    @Autowired
    public ActivityMinuteCSVService(ActivityMinuteRepository repository, ActivityMinuteFileProcessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException, InvalidFileTypeException {

        List<ActivityMinute> activityMinutes = fileProcessor.parseCSV(file);

        repository.saveAll(activityMinutes);
    }
}
