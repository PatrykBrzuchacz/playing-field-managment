package engineersthesis.playingfieldmanagment.application.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.Geometry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleMapDto {

    private String apiId;
    private String name;
    private GooglePlaceLocationDto location;
    private boolean isRegistered = false;

    public GoogleMapDto(String apiId, String name, GooglePlaceLocationDto location, boolean isRegistered) {
        this.apiId = apiId;
        this.name = name;
        this.location=location;
        this.isRegistered=isRegistered;
    }
}
