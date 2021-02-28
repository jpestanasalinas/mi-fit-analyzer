package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityStage;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityStageRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.ActivityStageFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Qualifier("activityStageCSVService")
public class ActivityStageCSVService implements CSVService {

    private final ActivityStageRepository repository;

    private final ActivityStageFileProcessor fileProcessor;

    @Autowired
    public ActivityStageCSVService(ActivityStageRepository repository, ActivityStageFileProcessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        List<ActivityStage> activityStages = fileProcessor.parseCSV(file);

        repository.saveAll(activityStages);
    }
}
