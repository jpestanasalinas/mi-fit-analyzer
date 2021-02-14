package dev.jpestana.mifitanalyzer.DataImporter;

import dev.jpestana.mifitanalyzer.DataImporter.Exceptions.InvalidFileTypeException;
import dev.jpestana.mifitanalyzer.DataImporter.Services.ActivityCSVService;
import dev.jpestana.mifitanalyzer.DataImporter.Services.ActivityMinuteCSVService;
import dev.jpestana.mifitanalyzer.DataImporter.Services.ActivityStageCSVService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static dev.jpestana.mifitanalyzer.DataImporter.DataImporterController.COULD_NOT_PROCESS_FILE_MESSAGE;
import static dev.jpestana.mifitanalyzer.DataImporter.DataImporterController.INVALID_FILE_MESSAGE;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DataImporterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityCSVService activityCSVService;
    @MockBean
    private ActivityMinuteCSVService activityMinuteCSVService;
    @MockBean
    private ActivityStageCSVService activityStageCSVService;

    @Test
    void whenInvalidFileTypeExceptionShouldReturnBadRequest() throws Exception {
        String content = "";
        MockMultipartFile file = new MockMultipartFile("file", "","text/json", content.getBytes());

        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(activityCSVService).save(file);
        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(activityMinuteCSVService).save(file);
        doThrow(new InvalidFileTypeException(INVALID_FILE_MESSAGE)).when(activityStageCSVService).save(file);

        mockMvc.perform(multipart("/import/activity-data").file(file))
                .andExpect(status().isBadRequest());
        mockMvc.perform(multipart("/import/activity-minute-data").file(file))
                .andExpect(status().isBadRequest());
        mockMvc.perform(multipart("/import/activity-stage-data").file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenIOExceptionShouldReturnBadRequest() throws Exception {
        String content = "";
        MockMultipartFile file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(activityCSVService).save(file);
        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(activityMinuteCSVService).save(file);
        doThrow(new IOException(COULD_NOT_PROCESS_FILE_MESSAGE)).when(activityStageCSVService).save(file);

        mockMvc.perform(multipart("/import/activity-data").file(file))
                .andExpect(status().isExpectationFailed());
        mockMvc.perform(multipart("/import/activity-minute-data").file(file))
                .andExpect(status().isExpectationFailed());
        mockMvc.perform(multipart("/import/activity-stage-data").file(file))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    void whenNoExceptionShouldReturnOk() throws Exception {
        String content = "";
        MockMultipartFile file = new MockMultipartFile("file", "file","text/csv", content.getBytes());

        mockMvc.perform(multipart("/import/activity-data").file(file))
                .andExpect(status().isOk());
        mockMvc.perform(multipart("/import/activity-minute-data").file(file))
                .andExpect(status().isOk());
        mockMvc.perform(multipart("/import/activity-stage-data").file(file))
                .andExpect(status().isOk());
    }

}