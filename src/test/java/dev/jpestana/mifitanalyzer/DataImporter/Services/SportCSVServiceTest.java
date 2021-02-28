package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Sport;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.SportRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.SportFileProcessor;
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

class SportCSVServiceTest {

    @InjectMocks
    private SportCSVService service;

    @Mock
    private SportRepository repository;

    private SportFileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        repository = mock(SportRepository.class);
        fileProcessor = new SportFileProcessor();
        service = new SportCSVService(repository, fileProcessor);
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
        String content = "\uFEFFtype,startTime,sportTime,distance,maxPace,minPace,avgPace,calories";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFtype,startTime,sportTime,distance,maxPace,minPace,avgPace,calories\n" +
                "0,\"2020-02-05 00:00:00\",3600,14000,400,10,150,8000";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        List<Sport> sports = Collections.singletonList(
                new Sport(
                        0,
                        Timestamp.valueOf("2020-02-05 00:00:00"),
                        3600,
                        14000,
                        400,
                        10,
                        150,
                        8000f
                )
        );

        service.save(file);

        verify(repository, times(1)).saveAll(sports);
    }

}