package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Activity;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.Mappers.ActivityFileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ActivityCSVServiceTest {

    @InjectMocks
    private ActivityCSVService service;

    @Mock
    private ActivityRepository repository;

    private ActivityFileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        repository = mock(ActivityRepository.class);
        fileProcessor = new ActivityFileProcessor();
        service = new ActivityCSVService(repository, fileProcessor);
    }

    @Test
    void givenANullByteStreamFileWhenSaveThenThrowsRuntimeException() {
        MultipartFile  file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(RuntimeException.class, () -> service.save(file));
    }

    @Test
    void givenANotCSVFileWhenSaveThenThrowsInvalidFileTypeException() {
        MultipartFile  file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(InvalidFileTypeException.class, () -> service.save(file));
    }

    @Test
    public void givenAEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,lastSyncTime,steps,distance,runDistance,calories";
        MultipartFile  file = new MockMultipartFile("file", "file", "text/csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    public void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,lastSyncTime,steps,distance,runDistance,calories\n" +
                "\"2020-02-05\",\"2020-02-05\",52,10,5,123 ";
        MultipartFile  file = new MockMultipartFile("file", "file", "text/csv", content.getBytes());

        List<Activity> activities = Collections.singletonList(
                new Activity(
                        Date.valueOf("2020-02-05"),
                        Date.valueOf("2020-02-05"),
                        52,
                        10f,
                        5f,
                        123f)
        );

        service.save(file);

        verify(repository, times(1)).saveAll(activities);
    }
}