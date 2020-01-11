package engineersthesis.playingfieldmanagment.modules.user.conversation;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Conversation findByUserOne_IdOrUserTwo_Id(Long id, Long id1);

    Conversation findByUserOne_IdAndUserTwo_Id(Long id, Long id1);

    Page<Conversation> findAllByUserOneOrUserTwo(User loggedUser, User loggedUser1, Pageable pageable);
}
