package engineersthesis.playingfieldmanagment.modules.playingField.availability.match;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.PlayingFieldAvailability;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.contact.MatchContact;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.reservation.Reservation;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.team.Team;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Data
@Table(name = "game")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_date")
    private LocalDate matchFromDate;

    @Column(name = "from_time")
    private LocalTime matchFromTime;

    @Column(name = "to_time")
    private LocalTime matchToTime;

    @ManyToOne
    @JoinColumn(name = "playing_field_availability_id")
    private PlayingFieldAvailability playingFieldAvailability;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_full")
    private Boolean isFull = false;

    @Column(name = "is_private")
    private Boolean isPrivate = false;

    @Column(name = "is_booked")
    private Boolean isBooked = false;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "match", orphanRemoval = true)
    private MatchContact matchContact;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();

    private String code;

    @Column(name = "booked_date")
    private LocalDateTime bookedDate;

    @Column(name = "is_code_filled")
    private Boolean isCodeFilled =false;
    public Match(LocalDate matchFromDate, LocalTime matchFromTime, LocalTime matchToTime, PlayingFieldAvailability playingFieldAvailability) {
        this.matchFromDate = matchFromDate;
        this.matchFromTime = matchFromTime;
        this.matchToTime = matchToTime;
        this.playingFieldAvailability = playingFieldAvailability;
    }

    public void resetTeams(Match match) {
        teams.removeAll(match.getTeams());
    }
}
