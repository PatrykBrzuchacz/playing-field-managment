package engineersthesis.playingfieldmanagment.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.Geometry;
import engineersthesis.playingfieldmanagment.application.model.dto.PlayingFieldSaveDto;
import engineersthesis.playingfieldmanagment.common.security.model.User;
import engineersthesis.playingfieldmanagment.common.security.model.WorkerRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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
    @OneToOne
    private User user;

    @OneToOne(mappedBy = "playingField", cascade = CascadeType.ALL)
    private WorkerRequest workerRequest;

    public PlayingField(String apiId, String name, double lat, double lng, String address){
        this.apiId=apiId;
        this.name=name;
        this.lat=lat;
        this.lng=lng;
        this.address=address;
    }
    public static PlayingField createPlayingFieldFromDto(PlayingFieldSaveDto playingFieldSaveDto){
        return new PlayingField(playingFieldSaveDto.getApiId(),playingFieldSaveDto.getName(),
                playingFieldSaveDto.getLat(),playingFieldSaveDto.getLng(),playingFieldSaveDto.getFormattedAddress());

    }
}
