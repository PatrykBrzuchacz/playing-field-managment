package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest.FriendsRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class FriendsRequestDto {
    private Long id;
    private String senderUsername;
    @Enumerated(value = EnumType.STRING)
    private FriendsRequestStatus requestStatus;
}

