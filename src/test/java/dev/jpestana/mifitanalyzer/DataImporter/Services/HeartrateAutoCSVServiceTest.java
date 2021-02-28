package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.HeartrateAuto;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.HeartrateAutoRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.HeartrateAutoFileProcessor;
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

class HeartrateAutoCSVServiceTest {

    @InjectMocks
    private HeartrateAutoCSVService service;

    @Mock
    private HeartrateAutoRepository repository;

    private HeartrateAutoFileProcessor fileProccessor;

    @BeforeEach
    void setUp() {
        repository = mock(HeartrateAutoRepository.class);
        fileProccessor = new HeartrateAutoFileProcessor();
        service = new HeartrateAutoCSVService(repository,fileProccessor);
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
        String content = "\uFEFFdate,time,heartRate";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,time,heartRate\n" +
                "\"2020-02-05\",\"2020-02-05 00:00:00\",60";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        List<HeartrateAuto> heartrates = Collections.singletonList(
                new HeartrateAuto(
                        Date.valueOf("2020-02-05"),
                        Timestamp.valueOf("2020-02-05 00:00:00"),
                        60
                )
        );

        service.save(file);

        verify(repository, times(1)).saveAll(heartrates);
    }

}