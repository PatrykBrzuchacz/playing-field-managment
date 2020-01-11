package engineersthesis.playingfieldmanagment.modules.playingField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.PlayingFieldAvailability;
import engineersthesis.playingfieldmanagment.modules.playingField.setup.PlayingFieldSetup;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.model.WorkerRequest;
import engineersthesis.playingfieldmanagment.web.dto.PlayingFieldSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "playing_field")
public class PlayingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_id")
    private String apiId;

    private String name;

    private double lat;
    private double lng;

    private String address;
    @Column(name = "is_registered")
    private boolean isRegistered = false;

    @JoinColumn(name = "user_id")
    @OneToOne()
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "playingField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkerRequest> workerRequest;

    @OneToMany(mappedBy = "playingField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayingFieldAvailability> playingFieldAvailabilities;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "playingField")
    private PlayingFieldSetup playingFieldSetup;

    @Column(name = "rate")
    private Double rating;
    @Column(name = "number_of_votes")
    private Integer numberOfVotes;

    public PlayingField(String apiId, String name, double lat, double lng, String address) {
        this.apiId = apiId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public static PlayingField createPlayingFieldFromDto(PlayingFieldSaveDto playingFieldSaveDto) {
        return new PlayingField(playingFieldSaveDto.getApiId(), playingFieldSaveDto.getName(),
                playingFieldSaveDto.getLat(), playingFieldSaveDto.getLng(), playingFieldSaveDto.getFormattedAddress());
    }
}
