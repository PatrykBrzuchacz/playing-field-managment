package engineersthesis.playingfieldmanagment.modules.playingField.availability.ban;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "playing_field_id")
    @ManyToOne
    private PlayingField playingField;

    @Column(name = "ban_date")
    private LocalDateTime banDate;

    public Ban(User user, PlayingField playingField, LocalDateTime banDate) {
        this.user = user;
        this.playingField = playingField;
        this.banDate = banDate;
    }
}
