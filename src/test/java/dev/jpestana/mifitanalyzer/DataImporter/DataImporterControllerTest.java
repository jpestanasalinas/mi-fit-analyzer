package dev.jpestana.mifitanalyzer.DataImporter;

import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static dev.jpestana.mifitanalyzer.DataImporter.DataImporterController.COULD_NOT_PROCESS_FILE_MESSAGE;
import static dev.jpestana.mifitanalyzer.DataImporter.DataImporterController.INVALID_FILE_MESSAGE;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DataImporterControllerTest {

    public static final List<String> URL_PATHS = Arrays.asList(
            "/import/activity-data",
            "/import/activity-minute-data",
            "/import/activity-stage-data",
            "/import/body-data",
            "/import/heartrate-data",
            "/import/heartrate-auto-data"
    );

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityCSVService activityCSVService;
    @MockBean
    private ActivityMinuteCSVService activityMinuteCSVService;
    @MockBean
    private ActivityStageCSVService activityStageCSVService;
    @MockBean
    private BodyCSVService bodyCSVService;
    @MockBean
    private HeartrateCSVService heartrateCSVService;
    @MockBean
    private HeartrateAutoCSVService heartrateAutoCSVService;

    @Test
    void whenInvalidFileTypeExceptionShouldReturnBadRequest() throws Exception {
        String content = "";
        MockMultipartFile file = new MockMultipartFile("file", "","text/json", content.getBytes());

        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(activityCSVService).save(file);
        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(activityMinuteCSVService).save(file);
        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(activityStageCSVService).save(file);
        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(bodyCSVService).save(file);
        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(heartrateCSVService).save(file);
        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(heartrateAutoCSVService).save(file);

        performRequestsExpecting(status().isBadRequest(), file);
    }

    @Test
    void whenIOExceptionShouldReturnBadRequest() throws Exception {
        String content = "";
        MockMultipartFile file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(activityCSVService).save(file);
        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(activityMinuteCSVService).save(file);
        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(activityStageCSVService).save(file);
        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(bodyCSVService).save(file);
        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(heartrateCSVService).save(file);
        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(heartrateAutoCSVService).save(file);

        performRequestsExpecting(status().isExpectationFailed(), file);
    }

    @Test
    void whenNoExceptionShouldReturnOk() throws Exception {
        String content = "";
        MockMultipartFile file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        doNothing().when(activityCSVService).save(file);
        doNothing().when(activityMinuteCSVService).save(file);
        doNothing().when(activityStageCSVService).save(file);
        doNothing().when(bodyCSVService).save(file);
        doNothing().when(heartrateCSVService).save(file);
        doNothing().when(heartrateAutoCSVService).save(file);

        performRequestsExpecting(status().isOk(), file);
    }

    private void performRequestsExpecting(ResultMatcher status, MockMultipartFile file) throws Exception {
        for(String urlPath: URL_PATHS) {
            mockMvc.perform(multipart(urlPath).file(file))
                    .andExpect(status);
        }
    }

}