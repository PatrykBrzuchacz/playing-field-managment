package engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRequestRepository extends JpaRepository<FriendsRequest, Long> {
    boolean existsBySenderAndReceiverAndRequestStatus(User sender, User receiver,
                                                      FriendsRequestStatus friendsRequestStatus);

    Page<FriendsRequest> findAllByReceiverAndRequestStatus(User user, FriendsRequestStatus requestStatus, Pageable pageable);

    List<FriendsRequest> findAllBySenderAndRequestStatus(User loggedUser, FriendsRequestStatus sended);

    boolean existsBySender_IdAndReceiver_IdAndRequestStatus(Long id, Long id1, FriendsRequestStatus sended);
}
