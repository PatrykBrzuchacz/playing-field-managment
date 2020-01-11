package engineersthesis.playingfieldmanagment.modules.playingField.rating;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    boolean existsByUserAndPlayingField_Id(User loggedUser, Long id);
}
