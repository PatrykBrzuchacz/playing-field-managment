package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GooglePlaceLocationDto {
    private double lat;
    private double lng;
    private String formattedAddress;
}
