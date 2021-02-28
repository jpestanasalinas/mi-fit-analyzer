package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Sleep;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.SleepRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.SleepFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Qualifier("sleepCSVService")
public class SleepCSVService implements CSVService {

    private final SleepRepository repository;
    private final SleepFileProcessor fileProcessor;

    @Autowired
    public SleepCSVService(SleepRepository repository, SleepFileProcessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        List<Sleep> sleeps = fileProcessor.parseCSV(file);
        repository.saveAll(sleeps);
    }
}
