package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Heartrate;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.HeartrateRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.HeartrateFileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HeartrateCSVServiceTest {

    @InjectMocks
    private HeartrateCSVService service;

    @Mock
    private HeartrateRepository repository;

    private HeartrateFileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        repository = mock(HeartrateRepository.class);
        fileProcessor = new HeartrateFileProcessor();
        service = new HeartrateCSVService(repository, fileProcessor);
    }

    @Test
    void givenANullByteStreamFileWhenSaveThenThrowsRuntimeException() {
        MultipartFile file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(RuntimeException.class, () -> service.save(file));
    }

    @Test
    void givenANotCSVFileWhenSaveThenThrowsInvalidFileTypeException() {
        MultipartFile  file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(InvalidFileTypeException.class, () -> service.save(file));
    }

    @Test
    void givenAEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,lastSyncTime,heartRate,timestamp";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,lastSyncTime,heartRate,timestamp\n" +
                "\"2020-02-05\",\"2020-02-05 00:00:00\",60,\"2020-02-05 00:00:00\"";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        List<Heartrate> heartrates = Collections.singletonList(
                new Heartrate(
                        Date.valueOf("2020-02-05"),
                        Timestamp.valueOf("2020-02-05 00:00:00"),
                        60,
                        Timestamp.valueOf("2020-02-05 00:00:00")
                )
        );

        service.save(file);

        verify(repository, times(1)).saveAll(heartrates);
    }

}