package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.team.Position;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserUsernameDto {
    private String username;
    private Long id;
    private Position position;
}
