package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityMinuteRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.Mappers.ActivityMinuteFileProcessor;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ActivityMinuteCSVServiceTest {

    @InjectMocks
    private ActivityMinuteCSVService service;

    @Mock
    private ActivityMinuteRepository repository;

    private ActivityMinuteFileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        repository = mock(ActivityMinuteRepository.class);
        fileProcessor = new ActivityMinuteFileProcessor();
        service = new ActivityMinuteCSVService(repository, fileProcessor);
    }

    @Test
    public void givenANullByteStreamFileWhenSaveThenThrowsRuntimeException() {
        MultipartFile  file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(RuntimeException.class, () -> service.save(file));
    }

    @Test
    public void givenANotCSVFileWhenSaveThenThrowsInvalidFileTypeException() {
        MultipartFile  file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(InvalidFileTypeException.class, () -> service.save(file));
    }

    @Test
    public void givenAEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,time,steps";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    public void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,time,steps\n" +
                "\"2020-02-05\",\"2020-02-05 00:00:00\",52,10,5,123 ";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        List<ActivityMinute> activityMinutes = Collections.singletonList(
                new ActivityMinute(
                        Date.valueOf("2020-02-05"),
                        Timestamp.valueOf("2020-02-05 00:00:00"),
                        52)
        );

        service.save(file);

        verify(repository, times(1)).saveAll(activityMinutes);
    }
}