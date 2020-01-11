package engineersthesis.playingfieldmanagment.modules.user.friends;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest.FriendsRequestRepository;
import engineersthesis.playingfieldmanagment.web.dto.FriendsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {

    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private FriendsAssembler friendsAssembler;
    @Autowired
    private FriendsRequestRepository friendsRequestRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;

    @Autowired
    private UserRepository userRepository;

    public List<FriendsDto> getAllFriends(Long id) {
        User user = userRepository.getOne(id);
        return friendsAssembler.toDtoList(
                friendsRepository.findAllByUserOneOrUserTwo(user,
                        user), user.getId());
    }

    public void deleteFriend(Long id) {
        User userToDelete = userRepository.getOne(id);
        User loggedUser = securityUserHelper.getLoggedUser();
        Friends friends = friendsRepository.findByUserOneAndUserTwo(userToDelete, loggedUser);
        if (friends == null) {
            friends = friendsRepository.findByUserOneAndUserTwo(loggedUser, userToDelete);
        }
        friendsRepository.delete(friends);
    }

    public Boolean isFriend(Long id) {
        return friendsRepository.existsByUserOne_IdAndUserTwo_Id(id, securityUserHelper.getLoggedUser().getId()) ||
                friendsRepository.existsByUserOne_IdAndUserTwo_Id(securityUserHelper.getLoggedUser().getId(),id);
    }
}
