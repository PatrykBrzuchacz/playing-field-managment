package engineersthesis.playingfieldmanagment.modules.security.service;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.ban.BanRepository;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.invite.Invite;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.invite.InviteRepository;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.RoleName;
import engineersthesis.playingfieldmanagment.modules.security.model.Status;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.RoleRepository;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.team.Position;
import engineersthesis.playingfieldmanagment.modules.user.friends.Friends;
import engineersthesis.playingfieldmanagment.modules.user.friends.FriendsRepository;
import engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest.FriendsRequestRepository;
import engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest.FriendsRequestStatus;
import engineersthesis.playingfieldmanagment.web.dto.EditUserDto;
import engineersthesis.playingfieldmanagment.web.dto.UserDto;
import engineersthesis.playingfieldmanagment.web.dto.UserFriendDto;
import engineersthesis.playingfieldmanagment.web.dto.UserUsernameDto;
import engineersthesis.playingfieldmanagment.web.exception.securityException.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private FriendsRequestRepository friendsRequestRepository;
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private BanRepository banRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return new PageImpl<>(users.stream().map(it -> userAssembler.toDto(it)).collect(Collectors.toList()),pageable
                ,users.getTotalElements());
    }

    public UserDto getUser(Long id) {
        return userAssembler.toDto(userRepository.getOne(id));
    }

    @Transactional
    public void editUserProfile(EditUserDto editUserDto) {
        User user = userRepository.getOne(editUserDto.getId());
        user.setPosition(editUserDto.getPosition());
        user.setLastName(editUserDto.getLastName());
        if(editUserDto.getAge()!=null && isNumeric(editUserDto.getAge())) {
            user.setAge(Integer.parseInt(editUserDto.getAge()));
        }
        user.setFirstName(editUserDto.getFirstName());
        user.setEmail(editUserDto.getEmail());
        user.setPhoneNumber(editUserDto.getPhoneNumber());
        user.setCity(editUserDto.getCity());
        if (editUserDto.getAvatar() != null) {
            String fileName = StringUtils.cleanPath(editUserDto.getAvatar().getOriginalFilename());
            try {
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                user.setAvatar(editUserDto.getAvatar().getBytes());
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
        }
    }


    public List<UserFriendDto> getUsers(String city, Position position) {

        User loggedUser = securityUserHelper.getLoggedUser();
        List<User> users;
        List<UserFriendDto> userFriendDtos = new ArrayList<>();

        if (position == null && !city.equals("")) {
            users = userRepository.findAllByCity(city);
        } else if (city.equals("") && position != null) {
            users = userRepository.findAllByPosition(position);
        } else if (!city.equals("")) {
            users = userRepository.findAllByPositionAndCity(position, city);
        } else users = userRepository.findAll();
        List<UserDto> userDtos = userAssembler.toDtoList(users);

        if (loggedUser != null) {
            userDtos.forEach(userDto -> {
                if (friendsRepository.existsByUserOne_IdAndUserTwo_Id(loggedUser.getId(), userDto.getId()) ||
                        friendsRepository.existsByUserOne_IdAndUserTwo_Id(userDto.getId(), loggedUser.getId())) {
                    userFriendDtos.add(new UserFriendDto(userDto, true, false));
                } else if (friendsRequestRepository.existsBySender_IdAndReceiver_IdAndRequestStatus(loggedUser.getId(),
                        userDto.getId(), FriendsRequestStatus.SENDED) ||
                        friendsRequestRepository.existsBySender_IdAndReceiver_IdAndRequestStatus(userDto.getId(),
                                loggedUser.getId(), FriendsRequestStatus.SENDED)) {
                    userFriendDtos.add(new UserFriendDto(userDto, false, true));
                } else {
                    userFriendDtos.add(new UserFriendDto(userDto, false, false));

                }
            });
        } else {
            userDtos.forEach(userDto -> {
                userFriendDtos.add(new UserFriendDto(userDto, false, false));
            });
        }
        return userFriendDtos;
    }

    public UserDto getUserByUsername(String username) {
        return userAssembler.toDto(userRepository.findByUsername(username));
    }

    //todo, refactor
    public List<UserUsernameDto> getUsersUsername(Long id, Boolean onlyFriends) {
        User loggedUser = securityUserHelper.getLoggedUser();
        PlayingField playingField = matchRepository.getOne(id).getPlayingFieldAvailability().getPlayingField();
        List<Invite> invites = inviteRepository.findAllByMatchIdAndStatus(id, Status.SENDED);
        List<User> receivers = invites.stream().map(Invite::getReceiver).collect(Collectors.toList());
        List<User> users = userRepository.findAll();
        List<Friends> friends;
        List<User> friendsUser = new ArrayList<>();
        List<User> usersToReturn = new ArrayList<>();
        if (onlyFriends) {
            friends = friendsRepository.findAllByUserOneOrUserTwo(loggedUser,
                    loggedUser);
            friends.forEach(val -> {
                if (val.getUserOne().equals(loggedUser)) {
                    friendsUser.add(val.getUserTwo());
                } else friendsUser.add(val.getUserOne());
            });
            users.forEach(val -> {
                if (!receivers.contains(val) && friendsUser.contains(val)) {
                    usersToReturn.add(val);
                }
            });
        } else {
            users.forEach(val -> {
                if (!receivers.contains(val)) {
                    usersToReturn.add(val);
                }
            });
        }
        if (usersToReturn.contains(loggedUser)) {
            usersToReturn.remove(securityUserHelper.getLoggedUser());
        }

        return usersToReturn.stream().map(val -> {
            if (!banRepository.existsByPlayingField_IdAndUser_Id(playingField.getId(), val.getId())) {
                return toDto(val);

            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private UserUsernameDto toDto(User user) {
        return new UserUsernameDto(user.getUsername(), user.getId(), user.getPosition()
        );
    }

    @Transactional
    public void dismissWorker(Long id) {
        User user = userRepository.getOne(id);
        user.setRole(roleRepository.findByName(RoleName.ROLE_USER));
        user.getPlayingField().setUser(null);
        playingFieldRepository.delete(user.getPlayingField());
        user.setPlayingField(null);

    }

    @Transactional
    public void restoreAccount(Long id) {
        userRepository.getOne(id).setActive(true);
    }

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}

