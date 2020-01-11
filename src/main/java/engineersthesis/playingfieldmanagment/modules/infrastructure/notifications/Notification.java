package engineersthesis.playingfieldmanagment.modules.infrastructure.notifications;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications") //to delete
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "date_and_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime dateAndTime;

    private boolean displayed = false;

    @ManyToOne
    @JoinColumn(name = "received")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;

    @Column(name = "notification_type")
    private NotificationType notificationType;

    private Boolean accepted;

    public Notification(String title, LocalDateTime dateAndTime, boolean displayed, User receiver, User sender,
                        Long entityId, NotificationType notificationType) {
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.displayed = displayed;
        this.receiver = receiver;
        this.sender = sender;
        this.entityId = entityId;
        this.notificationType = notificationType;
    }


    @Column(name = "entity_id")
    private Long entityId;
}
