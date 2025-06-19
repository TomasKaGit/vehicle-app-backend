package lt.tomas.vehicle_app_backend.controller;

import lt.tomas.vehicle_app_backend.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Test
    public void testSendNotifications() throws Exception {
        // Atlik POST užklausą į /api/notifications/send
        mockMvc.perform(post("/api/notifications/send"))
                .andExpect(status().isOk());

        // Patikrink, ar iškviečiamas notificationService.sendNotifications()
        verify(notificationService, times(1)).sendNotifications();
    }
}