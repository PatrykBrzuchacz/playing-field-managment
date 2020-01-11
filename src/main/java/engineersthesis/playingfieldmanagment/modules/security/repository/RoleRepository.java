package engineersthesis.playingfieldmanagment.modules.security.repository;


import engineersthesis.playingfieldmanagment.modules.security.model.Role;
import engineersthesis.playingfieldmanagment.modules.security.model.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(RoleName name);
}
