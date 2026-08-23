//package org.example.notificationservice.IntegrationTests;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.notificationservice.controller.NotificationController;
//import org.example.notificationservice.domain.dto.EmailRequest;
//import org.example.notificationservice.services.EmailService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@EnableWebMvc
//class NotificationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @MockitoBean
//    private EmailService emailService;   // мокаем реальную отправку писем
//
//    @Test
//    void sendEmailTest() throws Exception {
//        EmailRequest request = new EmailRequest("test@example.com", "CREATE");
//
//        doNothing().when(emailService).sendNotification("test@example.com", "CREATE");
//
//        mockMvc.perform(post("/api/notifications/send")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk());
//    }
//}
