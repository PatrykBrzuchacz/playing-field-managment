package engineersthesis.playingfieldmanagment.modules.playingField.availability.ban;

import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.NotificationService;
import engineersthesis.playingfieldmanagment.modules.playingField.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.team.Team;
import engineersthesis.playingfieldmanagment.modules.team.TeamRepository;
import engineersthesis.playingfieldmanagment.web.dto.BanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BanService {

    @Autowired
    private BanRepository banRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private BanAssembler banAssembler;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private NotificationService notificationService;

    // unbookowanie meczu do dobrego testu
    @Transactional
    public BanDto banUser(Long playingFieldId, Long userId) {
        User user = userRepository.getOne(userId);
        User loggedUser = securityUserHelper.getLoggedUser();
        if (loggedUser.getPlayingField().getId().equals(playingFieldId) &&
                !banRepository.existsByPlayingField_IdAndUser_Id(playingFieldId,
                        userId)) {
            Ban ban = banRepository.save(new Ban(userRepository.getOne(userId),
                    playingFieldRepository.getOne(playingFieldId),
                    LocalDateTime.now()));
            notificationService.saveUserHasBeenBannedOnPfNotification(ban,user,loggedUser);
            List<Match> matches =
                    matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndUser_Id(playingFieldId,
                            userId);
            matches.forEach(matchToUnbook -> {
                matchToUnbook.setUser(null);
                matchToUnbook.setReservation(null);
                matchToUnbook.setIsPrivate(false);
                matchToUnbook.setIsBooked(false);
                matchToUnbook.getTeams().forEach(Team::removeAllPlayers);
                matchRepository.flush();
                matchToUnbook.resetTeams(matchToUnbook);
            });
            List<Team> teams =
                    teamRepository.findAllByPlayers_IdAndMatch_PlayingFieldAvailability_PlayingField_Id(userId, playingFieldId);
            teams.forEach(team -> team.removePlayer(user));
            return banAssembler.toDto(ban);
        }
        return null;
    }

    @Transactional
    public void unbanUser(Long playingFieldId, Long userId) {
        if (securityUserHelper.getLoggedUser().getPlayingField().getId().equals(playingFieldId) &&
                banRepository.existsByPlayingField_IdAndUser_Id(playingFieldId,
                        userId)) {
            banRepository.deleteByPlayingField_IdAndUser_Id(playingFieldId, userId);
        }
    }

    public List<BanDto> getBans(Long playingFieldId) {
        return banAssembler.toDtoList(banRepository.findAllByPlayingField_Id(playingFieldId));
    }

    public Boolean isBanned(Long playingFieldId, Long userId) {
        return banRepository.existsByPlayingField_IdAndUser_Id(playingFieldId, userId);
    }
}
