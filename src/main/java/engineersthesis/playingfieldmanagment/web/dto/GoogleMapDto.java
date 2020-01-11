package engineersthesis.playingfieldmanagment.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleMapDto {


    private Long id;
    private String apiId;
    private String name;
    private GooglePlaceLocationDto location;
    private boolean isRegistered = false;
    private UserDto userDto;
    private boolean active;
    private Double rate;
    public GoogleMapDto(String apiId, String name, GooglePlaceLocationDto location, boolean isRegistered) {
        this.apiId = apiId;
        this.name = name;
        this.location = location;
        this.isRegistered = isRegistered;
    }

    public GoogleMapDto(Long id, String apiId, String name, GooglePlaceLocationDto location, boolean isRegistered,
                        UserDto userDto, boolean active, Double rate) {
        this.apiId = apiId;
        this.name = name;
        this.location = location;
        this.isRegistered = isRegistered;
        this.userDto = userDto;
        this.id = id;
        this.active = active;
        this.rate= rate;

    }
}
