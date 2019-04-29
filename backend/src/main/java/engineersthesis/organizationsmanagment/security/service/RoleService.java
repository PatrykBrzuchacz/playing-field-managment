package engineersthesis.organizationsmanagment.security.service;

import engineersthesis.organizationsmanagment.security.model.Role;
import engineersthesis.organizationsmanagment.security.model.RoleName;
import engineersthesis.organizationsmanagment.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName(RoleName.ROLE_USER.name());
    }

}
