package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private LocalDateTime sendedDate;
    private String title;
    private Long senderId;
    private String senderUsername;
    private Long entityId;
    private NotificationType notificationType;
    private Boolean displayed;
    private Boolean accepted;

    public NotificationDto(Long id, LocalDateTime sendedDate, String title, Long senderId, String senderUsername, Long entityId, NotificationType notificationType, Boolean displayed, Boolean accepted) {
        this.id = id;
        this.sendedDate = sendedDate;
        this.title = title;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.entityId = entityId;
        this.notificationType = notificationType;
        this.displayed = displayed;
        this.accepted = accepted;
    }

    private Long externalId;
}
