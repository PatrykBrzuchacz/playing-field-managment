package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.Notification;
import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.NotificationService;
import engineersthesis.playingfieldmanagment.web.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationsController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public List<NotificationDto> getNotifications(){
        return notificationService.getNotifications();
    }

    @PutMapping("/notifications/{id}/setAsDisplayed")
    public void setAsDisplayed(@PathVariable("id") Long id, @RequestParam("accepted") Boolean accepted ) {
         notificationService.setAsDisplayed(id,accepted);
    }


    @PutMapping("/notifications/setAllAsDisplayed")
    public void setAllAsDisplayed() {
         notificationService.setAllAsDisplayed();
    }

}
