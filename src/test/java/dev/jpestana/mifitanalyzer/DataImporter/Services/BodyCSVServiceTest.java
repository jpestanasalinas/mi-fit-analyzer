package dev.jpestana.mifitanalyzer.DataImporter.Services;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Body;
import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Repositories.BodyRepository;
import dev.jpestana.mifitanalyzer.DataImporter.Services.FileProcessors.BodyFileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BodyCSVServiceTest {

    @InjectMocks
    private BodyCSVService service;

    @Mock
    private BodyRepository repository;

    private BodyFileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        repository = mock(BodyRepository.class);
        fileProcessor = new BodyFileProcessor();
        service = new BodyCSVService(repository, fileProcessor);
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
        String content = "\uFEFFtimestamp,weight,height,bmi,fatRate,bodyWaterRate,boneMass,metabolism,muscleRate,visceralFat,impedance";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        service.save(file);

        verify(repository, times(1)).saveAll(new ArrayList<>());
    }

    @Test
    void givenANotEmptyFileWhenSaveThenSaveAllIsCalledOnce() throws IOException {
        String content = "\uFEFFtimestamp,weight,height,bmi,fatRate,bodyWaterRate,boneMass,metabolism,muscleRate,visceralFat,impedance\n" +
                "\"2020-02-05 00:00:00\",80.00,1.80,60,10,42.5,20.1,172.6,45.0,10.0,23";
        MultipartFile  file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        List<Body> bodies = Collections.singletonList(
                new Body(
                        Timestamp.valueOf("2020-02-05 00:00:00"),
                        80f,
                        1.8f,
                        60,
                        10.0f,
                        42.5f,
                        20.1f,
                        172.6f,
                        45f,
                        10f,
                        23f
                )
        );

        service.save(file);

        verify(repository, times(1)).saveAll(bodies);
    }

}