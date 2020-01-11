package engineersthesis.playingfieldmanagment.modules.playingField.availability.invite;

import engineersthesis.playingfieldmanagment.modules.security.model.Status;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findAllByReceiverAndStatus(User loggedUser, Status sended);

    boolean existsBySenderAndReceiver_IdAndStatus(User loggedUSer, Long userId, Status sended);

    List<Invite> findAllByMatchIdAndStatus(Long id, Status sended);

    List<Invite> findAllByReceiver_IdAndStatus(Long id, Status sended);




    Page<Invite> findAllByReceiverAndStatusAndMatch_MatchFromDateGreaterThanEqual(User loggedUser, Status sended,
                                                                                  LocalDate now, Pageable pageable);
}
