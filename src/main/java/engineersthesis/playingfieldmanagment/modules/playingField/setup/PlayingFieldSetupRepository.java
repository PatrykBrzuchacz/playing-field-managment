package engineersthesis.playingfieldmanagment.modules.playingField.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayingFieldSetupRepository extends JpaRepository<PlayingFieldSetup, Long> {
}
