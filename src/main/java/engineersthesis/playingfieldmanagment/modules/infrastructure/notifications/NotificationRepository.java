package engineersthesis.playingfieldmanagment.modules.infrastructure.notifications;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiver(User loggedUser);

    List<Notification> findAllByReceiverAndDisplayedIsFalse(User loggedUser);
}
