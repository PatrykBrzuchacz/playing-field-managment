package engineersthesis.playingfieldmanagment.common.security.repository;

import engineersthesis.playingfieldmanagment.common.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(@Param("username") String username);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findAll();

    boolean existsUserByUsername(String username);


}
