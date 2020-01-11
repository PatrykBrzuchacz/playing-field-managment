package engineersthesis.playingfieldmanagment.modules.user.friends;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, FriendsKey> {
    List<Friends> findAllByUserOneOrUserTwo(User userOne, User userTwo);

    void deleteByUserOneAndUserTwoOrUserTwoAndUserOne(User loggedUser, User userToDelete, User userToDelete1, User loggedUser1);

    Friends findByUserOneAndUserTwo(User userToDelete, User loggedUser);

    boolean existsByUserOne_IdAndUserTwo_Id(Long id, Long id1);
}
