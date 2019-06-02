package engineersthesis.playingfieldmanagment.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GooglePlaceLocationDto {
    private double lat;
    private double lng;
    private String formattedAddress;
}
