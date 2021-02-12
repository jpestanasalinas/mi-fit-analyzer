package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.ActivityMinuteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ActivityMinuteCSVServiceTest {

    @InjectMocks
    private ActivityMinuteCSVService service;

    @Mock
    private ActivityMinuteRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ActivityMinuteRepository.class);
        service = new ActivityMinuteCSVService(repository);
    }

    @Test
    public void givenANullByteStreamFileWhenSaveThenThrowsRuntimeException() {
        MultipartFile  file = new MockMultipartFile("file", (byte[]) null);

        assertThrows(RuntimeException.class, () -> service.save(file));
    }

    @Test
    public void givenAEmptyFileWhenSaveThenSaveAllIsCalledOnce() {
        String content = "\uFEFFdate,time,steps";
        MultipartFile  file = new MockMultipartFile("ACTIVITY_1612547502385.csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    public void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() {
        String content = "\uFEFFdate,time,steps\n" +
                "\"2020-02-05\",\"2020-02-05 00:00:00\",52,10,5,123 ";
        MultipartFile  file = new MockMultipartFile("file", content.getBytes());

        List<ActivityMinute> activityMinutes = Arrays.asList(
                new ActivityMinute(
                        Date.valueOf("2020-02-05"),
                        Timestamp.valueOf("2020-02-05 00:00:00"),
                        52)
        );

        service.save(file);

        verify(repository, times(1)).saveAll(activityMinutes);
    }
}