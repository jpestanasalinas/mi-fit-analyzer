package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Heartrate;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.HeartrateRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.HeartrateFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Qualifier("heartrateCSVService")
public class HeartrateCSVService implements CSVService {

    private final HeartrateRepository repository;
    private final HeartrateFileProcessor fileProcessor;

    @Autowired
    public HeartrateCSVService(HeartrateRepository repository, HeartrateFileProcessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        List<Heartrate> heartrates = fileProcessor.parseCSV(file);
        repository.saveAll(heartrates);
    }
}
