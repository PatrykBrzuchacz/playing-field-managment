package engineersthesis.playingfieldmanagment.modules.playingField;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.PlayingFieldAvailability;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchRepository;
import engineersthesis.playingfieldmanagment.modules.playingField.setup.PlayingFieldSetup;
import engineersthesis.playingfieldmanagment.modules.playingField.setup.PlayingFieldSetupRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.team.Team;
import engineersthesis.playingfieldmanagment.web.dto.PlayingFieldSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayingFieldService {

    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private PlayingFieldSetupRepository playingFieldSetupRepository;
    @Autowired
    private MatchRepository matchRepository;

    public PlayingFieldSetupDto getPlayingFieldSetup(Long id) {
        PlayingField playingField = playingFieldRepository.getOne(id);
        if (playingField.getPlayingFieldSetup() != null) {

            return new PlayingFieldSetupDto(playingField.getPlayingFieldSetup().getDescription(),
                    playingField.getPlayingFieldSetup().getTeamSize(), playingField.getPlayingFieldSetup().getPfPhoto(),
                    playingField.getName());
        } else return null;
    }


    @Transactional
    public void updatePFSetup(Long id, PlayingFieldSetupDto playingFieldSetupDto) {
        PlayingField playingField = playingFieldRepository.getOne(id);
        playingField.setName(playingFieldSetupDto.getName());
        System.out.println(playingFieldSetupDto.getPfPhoto().toString());
        if (playingField.getPlayingFieldSetup() == null) {
            playingFieldSetupRepository.save(new PlayingFieldSetup(playingFieldSetupDto.getTeamSize(),
                    playingFieldSetupDto.getDescription(), playingFieldSetupDto.getPfPhoto(), playingField,
                    playingFieldSetupDto.getName()));
        } else {
            playingField.getPlayingFieldSetup().setDescription(playingFieldSetupDto.getDescription());
            playingField.getPlayingFieldSetup().setPfPhoto(playingFieldSetupDto.getPfPhoto());
            playingField.getPlayingFieldSetup().setTeamSize(playingFieldSetupDto.getTeamSize());
            playingField.getPlayingFieldSetup().setName(playingFieldSetupDto.getName());

        }
    }

    @Transactional
    public void setWarnings(Long id) {
        List<Match> matches =
                matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndBookedDateLessThanEqualAndIsCodeFilledAndCodeNotNull(id,
                        LocalDateTime.now(), false);

        matches.forEach(match->{
            User user = match.getUser();
            if(user.getWarning()==null) {
                user.setWarning(0);
            }
            user.setWarning(user.getWarning()+1);
         if(user.getWarning()>=3) {
             user.setBanned(true);
             user.getMatches().forEach(matchToUnbook -> {
                 matchToUnbook.setUser(null);
                 matchToUnbook.setReservation(null);
                 matchToUnbook.setIsPrivate(false);
                 matchToUnbook.setIsBooked(false);
                 matchToUnbook.getTeams().forEach(Team::removeAllPlayers);
                 matchToUnbook.setCode(null);
                 matchToUnbook.setBookedDate(null);

                 matchRepository.flush();
                 matchToUnbook.resetTeams(matchToUnbook);
             });
         }
            match.setIsCodeFilled(true);
        });
    }
}
