package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamDto {

    private Long id;
    private String teamName;
    private int size;
    private int maxSize;
    private List<PlayerDto> players;
}
