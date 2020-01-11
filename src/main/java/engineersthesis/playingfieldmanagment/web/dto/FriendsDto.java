package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.team.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FriendsDto {
    private String username;
    private Long id;
    private LocalDateTime lastLogin;
    private Position position;
    @Lob
    private byte[] avatar;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phoneNumber;

}
