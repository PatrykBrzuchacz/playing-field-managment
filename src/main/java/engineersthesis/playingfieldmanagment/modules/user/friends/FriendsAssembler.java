package engineersthesis.playingfieldmanagment.modules.user.friends;

import engineersthesis.playingfieldmanagment.web.dto.FriendsDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FriendsAssembler {

    public List<FriendsDto> toDtoList(List<Friends> friends, Long id) {
        List<FriendsDto> friendsDtos = new ArrayList<>();
        friends.forEach(val -> {
            if (val.getUserOne().getId().equals(id)) {
                friendsDtos.add(new FriendsDto(val.getUserTwo().getUsername(), val.getUserTwo().getId(),
                        val.getUserTwo().getLastLogin(), val.getUserTwo().getPosition(), val.getUserTwo().getAvatar()
                        ,val.getUserTwo().getFirstName(),val.getUserTwo().getLastName(), val.getUserTwo().getAge(),
                        val.getUserTwo().getEmail(),val.getUserTwo().getPhoneNumber()));
            } else if (val.getUserTwo().getId().equals(id)) {
                friendsDtos.add(new FriendsDto(val.getUserOne().getUsername(),
                        val.getUserOne().getId(), val.getUserOne().getLastLogin(), val.getUserOne().getPosition(),
                        val.getUserOne().getAvatar(),val.getUserOne().getFirstName(), val.getUserOne().getLastName(),
                        val.getUserOne().getAge(),val.getUserOne().getEmail(),val.getUserOne().getPhoneNumber()));
            } else {
                // todo exception
            }
        });

        return friendsDtos;
    }
}
