package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.modules.team.Position;
import lombok.Data;

@Data
public class RegisterUserDto {
    private UserCredentials userCredentials;
    private Position position;
}
