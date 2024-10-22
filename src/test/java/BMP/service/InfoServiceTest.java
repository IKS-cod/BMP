package BMP.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(InfoService.class)
class InfoServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BuildProperties buildProperties;
    @SpyBean
    private InfoService infoService;
    @Test
    void getInfoTest() {
        infoService.getInfo();
        verify(buildProperties, times(1)).getName();
        verify(buildProperties, times(1)).getVersion();
    }
}