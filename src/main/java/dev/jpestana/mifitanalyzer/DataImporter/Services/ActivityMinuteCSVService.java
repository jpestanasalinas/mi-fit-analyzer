package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityMinuteRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.Mappers.ActivityMinuteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ActivityMinuteCSVService implements CSVService {

    private ActivityMinuteRepository repository;

    @Autowired
    public ActivityMinuteCSVService(ActivityMinuteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(MultipartFile file) {
        try {
            ActivityMinuteMapper mapper = new ActivityMinuteMapper();
            List<ActivityMinute> activityMinutes = mapper.csvToActivities(file.getInputStream());
            repository.saveAll(activityMinutes);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

}
