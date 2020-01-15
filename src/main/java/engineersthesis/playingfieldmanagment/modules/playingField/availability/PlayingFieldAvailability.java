package engineersthesis.playingfieldmanagment.modules.playingField.availability;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "playing_field_availability")
@AllArgsConstructor
public class PlayingFieldAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "opened_date_pf")
    private LocalDate openDatePF;

    @NonNull
    @Column(name = "closed_date_pf")
    private LocalDate closeDatePF;

    @NonNull
    @Column(name = "opened_time_pf")
    private LocalTime openTimePF;

    @NonNull
    @Column(name = "closed_time_pf")
    private LocalTime closeTimePF;
    @NonNull
    @Column(name = "match_time")
    private int matchTime;

    @ManyToOne
    @JoinColumn(name = "playing_field_id")
    private PlayingField playingField;

    @OneToMany(mappedBy = "playingFieldAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matches = new ArrayList<>();


    public PlayingFieldAvailability() {
    }

    public PlayingFieldAvailability(LocalDate openDatePF, LocalDate closeDatePF, LocalTime openTimePF, LocalTime closeTimePF, int matchTime, PlayingField playingField) {
        this.openDatePF = openDatePF;
        this.closeDatePF = closeDatePF;
        this.openTimePF = openTimePF;
        this.closeTimePF = closeTimePF;
        this.matchTime = matchTime;
        this.playingField = playingField;
    }

    public void addMatch(Match match) {
        this.matches.add(match);
    }
    public void removeMatches(List<Match> matches) {
        this.matches.removeAll(matches);
    }
}
