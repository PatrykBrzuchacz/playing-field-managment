package engineersthesis.playingfieldmanagment.modules.team;


import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> players = new ArrayList<>();

    @Column(name = "team_name")
    private String teamName;

    @ManyToOne
    private Match match;

    @Column(name = "is_full")
    private Boolean isFull = false;

    // todo jeden team do wielu meczu
    public Team(Match match, String teamName) {
        this.match = match;
        this.teamName = teamName;
    }

    public void addPlayer(User user) {
        players.add(user);
        user.getTeams().add(this);
    }

    public void removePlayer(User user) {
        players.remove(user);
        user.getTeams().remove(this);
    }

    public void removeAllPlayers() {
        players.removeAll(players);
    }
}
