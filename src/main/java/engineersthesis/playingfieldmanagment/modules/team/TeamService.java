package engineersthesis.playingfieldmanagment.modules.team;

import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.NotificationService;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.web.dto.TeamDto;
import engineersthesis.playingfieldmanagment.web.exception.TeamIsFullException;
import engineersthesis.playingfieldmanagment.web.exception.UserIsActuallyInThisTeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamAssembler teamAssembler;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

    public List<TeamDto> getTeamsByMatchId(Long id) {
        return teamAssembler.toDtoList(teamRepository.findAllByMatch(matchRepository.getOne(id)));
    }

    @Transactional
    public void joinToTeam(Long id) {
        Team team = teamRepository.getOne(id);
        User loggedUser = securityUserHelper.getLoggedUser();
        if (team.getPlayers().contains(id)) {
            throw new UserIsActuallyInThisTeamException();
        }
        if (team.getIsFull()) {
            throw new TeamIsFullException();
        }
        if (team.getMatch().getIsPrivate()) {
            throw new RuntimeException("Team is private, you cannot join without invite");
        }
        team.addPlayer(loggedUser);
        notificationService.saveUserJoinYourTeamNotification(team.getMatch(), team.getMatch().getUser(), loggedUser);
        List<Team> matchTeams = team.getMatch().getTeams();
        boolean isBothFull = true;
        for (Team it : matchTeams) {
            if (it.getPlayers().size() == it.getMatch().getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize()) {
                it.setIsFull(true);
            } else {
                isBothFull = false;
            }
        }
        if (isBothFull) {
            team.getMatch().setIsFull(true);
        }
    }

    @Transactional
    public void exitTeam(Long id) {
        Team team = teamRepository.getOne(id);
        User loggedUser = securityUserHelper.getLoggedUser();
        if (team.getMatch().getUser().getId().equals(loggedUser.getId())) {
            int teamsSize = 0;
            for (Team it : team.getMatch().getTeams()) {
                teamsSize = teamsSize + it.getPlayers().size();
            }
            if (teamsSize < 2) {
                team.getMatch().setUser(null);
            } else {
                team.getMatch().setUser(team.getPlayers().stream().filter(player -> player != loggedUser).findFirst().orElse(userRepository.getOne(1L)));
            }
            team.removePlayer(loggedUser);
        } else {
            if (team.getMatch().getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize() == team.getPlayers().size()) {
                team.getMatch().setIsFull(false);
                if (team.getIsFull()) {
                    team.setIsFull(false);
                }
            }
            team.removePlayer(loggedUser);
        }
    }

    @Transactional
    public void removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = teamRepository.getOne(teamId);
        User playerToRemove = userRepository.getOne(playerId);

        if (securityUserHelper.getLoggedUser().getId().equals(team.getMatch().getUser().getId())) {
            if (!team.getPlayers().contains(playerToRemove)) {
                throw new RuntimeException("User is not in this team");
            }
            if (team.getMatch().getUser().getId().equals(playerId)) {
                throw new RuntimeException("You cannot remove yourself from team");
            }
            team.removePlayer(playerToRemove);
        } else throw new RuntimeException("User is not owner of this match");
    }

    @Transactional
    public void swapTeams(Long id) {
        User loggedUser = securityUserHelper.getLoggedUser();
        Match match = matchRepository.getOne(id);
        Team firstTeam = match.getTeams().get(0);
        Team secondTeam = match.getTeams().get(1);
        if (firstTeam.getPlayers().contains(loggedUser)) {
            if (secondTeam.getPlayers().size() < match.getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize()) {
                firstTeam.removePlayer(loggedUser);
                secondTeam.addPlayer(loggedUser);
            } else {
                throw new RuntimeException("Cannot swap because team is full");
            }
        } else if (secondTeam.getPlayers().contains(loggedUser)) {
            if (firstTeam.getPlayers().size() < match.getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize()) {
                secondTeam.removePlayer(loggedUser);
                firstTeam.addPlayer(loggedUser);
            } else {
                throw new RuntimeException("Cannot swap because team is full");
            }
        } else throw new RuntimeException("Player is not in this match yet");

    }

    //todo add more teams to one match
}
