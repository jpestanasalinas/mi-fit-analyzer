package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityStage;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityStageRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.ActivityStageFileProccessor;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityStageCSVServiceTest {

    @InjectMocks
    private ActivityStageCSVService service;

    @Mock
    private ActivityStageRepository repository;

    private ActivityStageFileProccessor fileProccessor;

    @BeforeEach
    void setUp() {
        repository = mock(ActivityStageRepository.class);
        fileProccessor = new ActivityStageFileProccessor();
        service = new ActivityStageCSVService(repository,fileProccessor);
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
        String content = "\uFEFFdate,start,stop,distance,calories,steps";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFdate,start,stop,distance,calories,steps\n" +
                "\"2020-02-05\",\"2020-02-05 00:00:00\",\"2020-02-05 00:01:00\",52,52,3000";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        List<ActivityStage> activityStages = Collections.singletonList(
                new ActivityStage(
                        Date.valueOf("2020-02-05"),
                        Timestamp.valueOf("2020-02-05 00:00:00"),
                        Timestamp.valueOf("2020-02-05 00:01:00"),
                        52f,
                        52f,
                        3000)
        );

        service.save(file);

        verify(repository, times(1)).saveAll(activityStages);
    }

}