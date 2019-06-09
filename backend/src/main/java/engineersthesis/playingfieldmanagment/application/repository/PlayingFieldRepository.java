package engineersthesis.playingfieldmanagment.application.repository;

import engineersthesis.playingfieldmanagment.application.model.PlayingField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayingFieldRepository extends JpaRepository<PlayingField, Long> {
    PlayingField findByApiId(String apiId);
    boolean existsPlayingFieldByApiId(String apiId);
}
