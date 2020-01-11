package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InviteWithUserDto {
    private Long id;
    private UserUsernameDto user;
}
