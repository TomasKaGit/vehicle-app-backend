package lt.tomas.vehicle_app_backend.controller;

import lt.tomas.vehicle_app_backend.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {


    private final NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    //  Išsiųsti pranešimus (notif)
    // ✅ URL: POST /api/notifications/send

    @PostMapping("/send")
    public void send() {
        notificationService.sendNotifications();
    }
}
