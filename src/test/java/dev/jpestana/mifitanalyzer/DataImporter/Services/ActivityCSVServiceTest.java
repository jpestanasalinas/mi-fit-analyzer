package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Activity;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ActivityCSVServiceTest {

    @InjectMocks
    private ActivityCSVService service;

    @Mock
    private ActivityRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ActivityRepository.class);
        service = new ActivityCSVService(repository);
    }

    @Test
    public void givenANullByteStreamFileWhenSaveThenThrowsRuntimeException() {
        MultipartFile  file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(RuntimeException.class, () -> service.save(file));
    }

    @Test
    public void givenAEmptyFileWhenSaveThenSaveAllIsCalledOnce() {
        String content = "\uFEFFdate,lastSyncTime,steps,distance,runDistance,calories";
        MultipartFile  file = new MockMultipartFile("ACTIVITY_1612547502385.csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<Activity>());
    }

    @Test
    public void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() {
        String content = "\uFEFFdate,lastSyncTime,steps,distance,runDistance,calories\n" +
                "\"2020-02-05\",\"2020-02-05\",52,10,5,123 ";
        MultipartFile  file = new MockMultipartFile("file", content.getBytes());

        List<Activity> activities = Arrays.asList(
                new Activity(
                        Date.valueOf("2020-02-05"),
                        Date.valueOf("2020-02-05"),
                        Integer.valueOf(52),
                        Float.valueOf(10),
                        Float.valueOf(5),
                        Float.valueOf(123))
        );

        service.save(file);

        verify(repository, times(1)).saveAll(activities);
    }
}