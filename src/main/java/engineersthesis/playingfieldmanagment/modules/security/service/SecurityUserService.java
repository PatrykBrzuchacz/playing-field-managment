package engineersthesis.playingfieldmanagment.modules.security.service;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.web.dto.RegisterUserDto;
import engineersthesis.playingfieldmanagment.web.dto.UserDto;
import engineersthesis.playingfieldmanagment.web.dto.UserUsername;
import engineersthesis.playingfieldmanagment.web.exception.securityException.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;

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

    public void createUserWithPosition(RegisterUserDto registerUserDto) {
        User newUser = assignUserData(registerUserDto.getUserCredentials());
        newUser.setPosition(registerUserDto.getPosition());
        userRepository.save(newUser);


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

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);

        if (user == null || !user.isActive()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        user.setActive(false);
    }

    @Transactional
    public void banUser(Long id) {
        User user = userRepository.getOne(id);

        if (user == null || user.isBanned()) {
            throw new UsernameNotFoundException("User doesn't exist or its actually banned");
        }
        user.setBanned(true);
    }
    @Transactional
    public void unbanUser(Long id) {
        User user = userRepository.getOne(id);

        if (user == null || (!user.isBanned())) {
            throw new UsernameNotFoundException("User doesn't exists or itsnt actually banned");
        }
        user.setBanned(false);
        user.setWarning(0);
    }

    public UserDto getLoggedUserWithAvatar() {
        User user = securityUserHelper.getLoggedUser();
        PlayingField playingField = playingFieldRepository.findByUser(user);
        UserDto userDto = userAssembler.toDtoWithAvatar(user);

        if (playingField != null) {
            userDto.setPlayingFieldId(playingField.getId());
        }

        return userDto;
    }

    public UserUsername getUserUsername(Long id) {
        User user = userRepository.getOne(id);
        return new UserUsername(user.getUsername());
    }

    public UserDto getLoggedUser() {
        User user = securityUserHelper.getLoggedUser();
        PlayingField playingField = playingFieldRepository.findByUser(user);
        UserDto userDto = userAssembler.toDto(user);

        if (playingField != null) {
            userDto.setPlayingFieldId(playingField.getId());
        }

        return userDto;
    }
}

