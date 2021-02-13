package dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors;

import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class FileProcessor<T> {

    public static String TYPE = "text/csv";

    public FileProcessor() {
    }

    public List<T> parseCSV(MultipartFile file) throws IOException, InvalidFileTypeException {

        if(!hasCSVFormat(file)) {
            throw new InvalidFileTypeException("file type is not compatible, only CSV accepted");
        }

        InputStream is = file.getInputStream();
        try(BufferedInputStream buffIs = new BufferedInputStream(is);
            BOMInputStream bomIn = new BOMInputStream(buffIs)) {

            final BufferedReader buffReadr = chooseBufferedReader(buffIs, bomIn);
            return map(buffReadr);

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public BufferedReader chooseBufferedReader(BufferedInputStream buffIs, BOMInputStream bomIn) throws IOException {
        final boolean hasBOM = bomIn.hasBOM();
        final BufferedReader buffReadr = new BufferedReader(
                new InputStreamReader(hasBOM ? bomIn : buffIs, StandardCharsets.UTF_8));
        if (!hasBOM) {
            buffIs.reset();
        }
        return buffReadr;
    }

    protected abstract List<T> map(BufferedReader buffReadr) throws IOException;

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
