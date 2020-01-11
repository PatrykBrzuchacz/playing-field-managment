package engineersthesis.playingfieldmanagment.modules.playingField.availability.invite;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.security.model.Status;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Invite(User sender, User receiver, Match match, Status status) {
        this.sender = sender;
        this.receiver = receiver;
        this.match = match;
        this.status = status;
    }

}
