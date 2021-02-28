package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.HeartrateAuto;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.HeartrateAutoRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.HeartrateAutoFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Qualifier("heartrateAutoCSVService")
public class HeartrateAutoCSVService implements CSVService {

    private final HeartrateAutoRepository repository;
    private final HeartrateAutoFileProcessor fileProcessor;

    @Autowired
    public HeartrateAutoCSVService(HeartrateAutoRepository repository, HeartrateAutoFileProcessor fileProcessor) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        List<HeartrateAuto> heartrates = fileProcessor.parseCSV(file);
        repository.saveAll(heartrates);
    }
}
