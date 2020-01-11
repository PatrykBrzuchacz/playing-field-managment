package engineersthesis.playingfieldmanagment.modules.security.repository;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.team.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);

    boolean existsUserByUsername(String username);

    List<User> findAllByPositionAndCity(Position position, String city);

    List<User> findAllByPosition(Position position);

    List<User> findAllByCity(String city);
    Page<User> findAll(Pageable pageable);
}
