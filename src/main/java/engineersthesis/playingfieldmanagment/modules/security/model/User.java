package engineersthesis.playingfieldmanagment.modules.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.invite.Invite;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.team.Position;
import engineersthesis.playingfieldmanagment.modules.team.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Table(name = "pf_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @NonNull
    private String username;

    @JsonIgnore
    @NonNull
    private String password;

    private Boolean banned = false;
    private Boolean active = true;


    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime registered;

    @Column(name = "last_login")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime lastLogin;

    @JsonIgnore
    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Position position;

    @Type(type="org.hibernate.type.BinaryType")
    @Lob
    private byte[] avatar;
    private Integer age;
    private String city;
    @Column(name = "first_name")
    private String firstName;
    @OneToOne(mappedBy = "user")
    private PlayingField playingField;

    // todo delete probably
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkerRequest> workerRequest;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "players")
    private List<Team> teams = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Match> matches = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Invite> invites = new ArrayList<>();


    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "last_name")
    private String lastName;
    private Integer warning;

    public User() {
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBanned() {
        return this.banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
