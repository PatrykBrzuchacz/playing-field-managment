package engineersthesis.playingfieldmanagment.modules.playingField;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayingFieldRepository extends JpaRepository<PlayingField, Long> {

    PlayingField findByUser(User user);

    PlayingField findByAddressAndIsRegisteredIsTrue(String formatted_address);
}
