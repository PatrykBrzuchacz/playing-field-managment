package engineersthesis.playingfieldmanagment.common.security.repository;


import engineersthesis.playingfieldmanagment.common.security.model.Role;
import engineersthesis.playingfieldmanagment.common.security.model.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(RoleName name);
}
