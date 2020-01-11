package engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest;

import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.NotificationService;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.user.friends.Friends;
import engineersthesis.playingfieldmanagment.modules.user.friends.FriendsRepository;
import engineersthesis.playingfieldmanagment.web.dto.FriendsDto;
import engineersthesis.playingfieldmanagment.web.dto.FriendsRequestDto;
import engineersthesis.playingfieldmanagment.web.exception.UserIsActuallyRequestedToBeFriendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendsRequestService {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private FriendsRequestRepository friendsRequestRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private FriendsRequestAssembler friendsRequestAssembler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

    public void sendRequest(Long id) {
        User user = userRepository.getOne(id);
        User loggedUser = securityUserHelper.getLoggedUser();
        if (friendsRequestRepository.existsBySenderAndReceiverAndRequestStatus(loggedUser,
                user, FriendsRequestStatus.SENDED)) {
            throw new UserIsActuallyRequestedToBeFriendException();
        }

        FriendsRequest friendsRequest = friendsRequestRepository.save(new FriendsRequest(loggedUser,
                user, FriendsRequestStatus.SENDED));
        notificationService.saveFriendRequestNotification(friendsRequest,user,loggedUser);
    }

    public Page<FriendsRequestDto> getAllUserRequests(Pageable pageable) {
        return friendsRequestAssembler.toDtoList(friendsRequestRepository.findAllByReceiverAndRequestStatus(securityUserHelper.getLoggedUser(),
                FriendsRequestStatus.SENDED, pageable),pageable);
    }

    @Transactional
    public FriendsDto acceptRequest(Long id) {
        FriendsRequest friendsRequest = friendsRequestRepository.getOne(id);
        if (friendsRequest.getRequestStatus() == FriendsRequestStatus.SENDED) {
            friendsRequest.setRequestStatus(FriendsRequestStatus.ACCEPTED);
            friendsRepository.save(new Friends(friendsRequest.getSender(), friendsRequest.getReceiver()));
        }
        return new FriendsDto(friendsRequest.getSender().getUsername(), friendsRequest.getSender().getId(),
                friendsRequest.getSender().getLastLogin(), friendsRequest.getSender().getPosition(),
                friendsRequest.getSender().getAvatar(), friendsRequest.getSender().getFirstName(),
                friendsRequest.getSender().getLastName(), friendsRequest.getSender().getAge(),
                friendsRequest.getSender().getEmail(),friendsRequest.getSender().getPhoneNumber());
    }

    @Transactional
    public void declineRequest(Long id) {
        FriendsRequest friendsRequest = friendsRequestRepository.getOne(id);
        if (friendsRequest.getRequestStatus() == FriendsRequestStatus.SENDED) {
            friendsRequest.setRequestStatus(FriendsRequestStatus.REJECTED);
            friendsRepository.save(new Friends(friendsRequest.getSender(), friendsRequest.getReceiver()));
        }
    }

    public List<String> getSendedRequests() {
        return friendsRequestAssembler.toSendedRequestsDtoList(friendsRequestRepository.findAllBySenderAndRequestStatus(securityUserHelper.getLoggedUser(), FriendsRequestStatus.SENDED));
    }
}
