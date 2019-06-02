package engineersthesis.playingfieldmanagment.application.model.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.Geometry;
import com.google.maps.model.OpeningHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    @JsonProperty("id")
    private String apiId;
    @JsonProperty("formatted_address")
    private String formatted_address;
    @JsonProperty("geometry")
    private Geometry geometry;
    @JsonProperty("name")
    private String name;

}

