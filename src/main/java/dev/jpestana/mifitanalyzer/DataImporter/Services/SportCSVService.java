package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Sport;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.SportRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.SportFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Qualifier("sportCSVService")
public class SportCSVService implements CSVService {

    private final SportRepository repository;
    private final SportFileProcessor fileProcessor;

    @Autowired
    public SportCSVService(SportRepository repository, SportFileProcessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        List<Sport> sports = fileProcessor.parseCSV(file);
        repository.saveAll(sports);
    }
}
