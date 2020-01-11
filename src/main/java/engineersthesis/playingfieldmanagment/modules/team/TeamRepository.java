package engineersthesis.playingfieldmanagment.modules.team;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByMatch(Match match);


    List<Team> findAllByPlayers_IdAndMatch_PlayingFieldAvailability_PlayingField_Id(Long userId, Long playingFieldId);

    Integer countByPlayers(User user);
}
