package dev.jpestana.mifitanalyzer.DataImporter.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CSVService {

    void save(MultipartFile file) throws IOException;

}
