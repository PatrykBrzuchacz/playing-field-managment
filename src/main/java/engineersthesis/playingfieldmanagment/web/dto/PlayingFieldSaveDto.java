package engineersthesis.playingfieldmanagment.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayingFieldSaveDto {
    private String apiId;
    private String name;
    private double lat;
    private double lng;
    private String formattedAddress;
    private Double rate;
    public PlayingFieldSaveDto() {
    }
}

