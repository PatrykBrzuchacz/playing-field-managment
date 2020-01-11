package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFriendDto {

    private UserDto userDto;
    private Boolean isFriend;
    private Boolean isRequestSended;
}
