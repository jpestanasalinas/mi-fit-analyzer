package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityStage;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityStageRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.ActivityStageFileProccessor;
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

    private final ActivityStageFileProccessor fileProccessor;

    @Autowired
    public ActivityStageCSVService(ActivityStageRepository repository, ActivityStageFileProccessor fileProccessor) {
        this.repository = repository;
        this.fileProccessor = fileProccessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        List<ActivityStage> activityStages = fileProccessor.parseCSV(file);

        repository.saveAll(activityStages);
    }
}
