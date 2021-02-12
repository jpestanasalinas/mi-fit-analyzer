package dev.jpestana.mifitanalyzer.DataImporter.Services;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {

    void save(MultipartFile file);
}
