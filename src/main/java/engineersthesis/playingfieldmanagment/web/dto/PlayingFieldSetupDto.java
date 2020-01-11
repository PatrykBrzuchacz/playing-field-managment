package engineersthesis.playingfieldmanagment.web.dto;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class PlayingFieldSetupDto {

    private String description;
    private int teamSize;
    @Lob
    private byte[] pfPhoto;
    private String name;

    public PlayingFieldSetupDto(String description, int teamSize, byte[] pfPhoto, String name) {
        this.description = description;
        this.teamSize = teamSize;
        this.pfPhoto = pfPhoto;
        this.name = name;
    }
}
