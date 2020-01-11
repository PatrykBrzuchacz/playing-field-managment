package engineersthesis.playingfieldmanagment.modules.playingField.rating;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playing_field_id")
    private PlayingField playingField;

    public Rate(PlayingField playingField, User user, Integer rating) {
        this.playingField = playingField;
        this.user = user;
        this.rating = rating;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating;

}
