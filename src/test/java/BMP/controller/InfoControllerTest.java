package BMP.controller;

import BMP.model.ServiceInfo;
import BMP.service.InfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(InfoController.class)
class InfoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InfoService infoService;



    @Test
    void getInfoTest() {
        ServiceInfo serviceInfo = new ServiceInfo("Test","1");
        when(infoService.getInfo()).thenReturn(serviceInfo);
        ServiceInfo actual = infoService.getInfo();
        Assertions.assertEquals(serviceInfo, actual);

    }
}