package engineersthesis.playingfieldmanagment.common.security.service;

import engineersthesis.playingfieldmanagment.common.security.exception.UsernameAlreadyExistsException;
import engineersthesis.playingfieldmanagment.common.security.exception.WorkerIsNotActiveException;
import engineersthesis.playingfieldmanagment.common.security.model.RoleName;
import engineersthesis.playingfieldmanagment.common.security.model.User;
import engineersthesis.playingfieldmanagment.common.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.common.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;


    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        if (user.isBanned()) {
            throw new UsernameNotFoundException("Your account has been banned");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName().toString()));

        return authorities;
    }


    public User createUser(UserCredentials user) {
        User newUser = assignUserData(user);
        return userRepository.save(newUser);
    }


    public User assignUserData(UserCredentials user) {
        checkUsername(user.getUsername());

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(roleService.getUserRole());
        newUser.setRegistered(LocalDateTime.now());
        return newUser;
    }

    private void checkUsername(String username) {
        boolean usernameExists = userRepository.existsUserByUsername(username);

        if (usernameExists) throw new UsernameAlreadyExistsException();
    }


    public User deleteUser(Long id) {
        User user = userRepository.getOne(id);

        if (user == null || !user.isActive()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        user.setActive(false);
        return userRepository.save(user);
    }

    public void banUser(Long id) {
        User user = userRepository.getOne(id);

        if (user == null || user.isBanned()) {
            throw new UsernameNotFoundException("User doesn't exist or its actually banned");
        }
        user.setBanned(true);
        userRepository.save(user);
    }

    public void unbanUser(Long id) {
        User user = userRepository.getOne(id);

        if (user == null || (!user.isBanned())) {
            throw new UsernameNotFoundException("User doesn't exists or itsnt actually banned");
        }
        user.setBanned(false);
        userRepository.save(user);
    }

}

