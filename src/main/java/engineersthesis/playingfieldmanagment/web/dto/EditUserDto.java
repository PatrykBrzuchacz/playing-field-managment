package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.team.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class EditUserDto {
    Long id;
    String age;
    String phoneNumber;
    MultipartFile avatar;
    Position position;
    String city;
    String firstName;
    String lastName;
    String email;
}
