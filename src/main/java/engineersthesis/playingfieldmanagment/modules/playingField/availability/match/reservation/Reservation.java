package engineersthesis.playingfieldmanagment.modules.playingField.availability.match.reservation;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;
    @Column(name = "last_name")
    private String lastName;

    @OneToOne(mappedBy = "reservation")
    private Match match;

    public Reservation(String email, String lastName, String phoneNumber, Match match) {
        this.email = email;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.match = match;
    }
}
