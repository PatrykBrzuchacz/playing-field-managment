package engineersthesis.playingfieldmanagment.modules.team;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.web.dto.PlayerDto;
import engineersthesis.playingfieldmanagment.web.dto.TeamDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamAssembler {
    public List<TeamDto> toDtoList(List<Team> teams) {
        return teams.stream().map(this::toDto).collect(Collectors.toList());
    }

    private TeamDto toDto(Team team) {
        return new TeamDto(team.getId(), team.getTeamName(), team.getPlayers().size(),
                team.getMatch().getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize(),
                toPlayersDtoList(team.getPlayers()));
    }

    private List<PlayerDto> toPlayersDtoList(List<User> users) {
        return users.stream().map(this::toPlayerDto).collect(Collectors.toList());
    }

    private PlayerDto toPlayerDto(User user) {
        return new PlayerDto(user.getId(), user.getUsername(), user.getPosition());
    }
}
