package engineersthesis.playingfieldmanagment.modules.security.service;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.team.TeamRepository;
import engineersthesis.playingfieldmanagment.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAssembler {

    @Autowired
    private TeamRepository teamRepository;
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.isBanned(), user.isActive(), user.getRegistered(),
                user.getLastLogin(), user.getEmail(), user.getPhoneNumber(), user.getLastName(),
                user.getAge(), user.getFirstName(), user.getPosition(),
                user.getCity(), null, teamRepository.countByPlayers(user), user.getWarning(),
                user.getPlayingField() !=null ? user.getPlayingField().getId():null);
    }

    public UserDto toDtoWithAvatar(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.isBanned(), user.isActive(), user.getRegistered(),
                user.getLastLogin(), user.getEmail(), user.getPhoneNumber(), user.getLastName(),
                user.getAge(), user.getFirstName(), user.getPosition(),
                user.getCity(), user.getAvatar(),teamRepository.countByPlayers(user), user.getWarning(),
                user.getPlayingField() !=null ? user.getPlayingField().getId():null);
    }
    public List<UserDto> toDtoList(List<User> users) {
        return users.stream().map(this::toDtoWithAvatar).collect(Collectors.toList());
    }
}
