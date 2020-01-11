package engineersthesis.playingfieldmanagment.modules.user.conversation.message;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderAndReceiver(User loggedUser, User user);
}
