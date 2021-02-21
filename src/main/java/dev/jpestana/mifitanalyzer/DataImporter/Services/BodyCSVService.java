package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Body;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.BodyRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.BodyFileProccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BodyCSVService implements CSVService {

    private final BodyRepository repository;
    private final BodyFileProccessor fileProcessor;

    @Autowired
    public BodyCSVService(BodyRepository repository, BodyFileProccessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        List<Body> bodies = fileProcessor.parseCSV(file);
        repository.saveAll(bodies);
    }
}
