package engineersthesis.playingfieldmanagment.modules.playingField.availability.match.contact;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "match_contact")
@NoArgsConstructor
public class MatchContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String surname;

    public MatchContact(Match match, String phoneNumber, String email, String surname) {
        this.match = match;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.surname = surname;
    }


}
