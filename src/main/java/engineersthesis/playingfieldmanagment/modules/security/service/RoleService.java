package engineersthesis.playingfieldmanagment.modules.security.service;

import engineersthesis.playingfieldmanagment.modules.security.model.Role;
import engineersthesis.playingfieldmanagment.modules.security.model.RoleName;
import engineersthesis.playingfieldmanagment.modules.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName(RoleName.ROLE_USER);
    }

}
