package engineersthesis.playingfieldmanagment.application.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class PlayingFieldSaveDto {
    private String apiId;
    private String name;
    private double lat;
    private double lng;
    private String formattedAddress;

    public PlayingFieldSaveDto(){}
}

