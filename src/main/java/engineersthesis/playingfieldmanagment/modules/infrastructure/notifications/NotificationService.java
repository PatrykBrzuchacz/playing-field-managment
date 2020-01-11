package engineersthesis.playingfieldmanagment.modules.infrastructure.notifications;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.ban.Ban;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.invite.Invite;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.team.Team;
import engineersthesis.playingfieldmanagment.modules.user.conversation.Conversation;
import engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest.FriendsRequest;
import engineersthesis.playingfieldmanagment.web.dto.AvailabilityWithMatchesDto;
import engineersthesis.playingfieldmanagment.web.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationsRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private NotificationAssembler notificationAssembler;

    public List<NotificationDto> getNotifications() {
        User loggedUser = securityUserHelper.getLoggedUser();

    return notificationAssembler.toDtoList(notificationsRepository.findAllByReceiver(loggedUser))
            .stream().sorted(Comparator.comparing(NotificationDto::getSendedDate).reversed()).collect(Collectors.toList());
    }

    @Transactional
    public void setAsDisplayed(Long notificationId,Boolean accepted) {
        Notification notification = notificationsRepository.getOne(notificationId);
        notification.setDisplayed(true);
        notification.setAccepted(accepted);
    }

    @Transactional
    public void setAllAsDisplayed() {
        User loggedUser = securityUserHelper.getLoggedUser();

        List<Notification> notifications = notificationsRepository.findAllByReceiverAndDisplayedIsFalse(loggedUser);

        notifications.forEach(val->{
            val.setDisplayed(true);
        });
    }

//    @Transactional
//    public void setAccepted(Long id) {
//        Notification notification = notificationsRepository.getOne(id);
//        notification.setAccepted(true);
//    }
    public void saveConversationNotification(Conversation conversation, User receiver, User sender) {
    notificationsRepository.save(new Notification("Masz nową wiadomość od " + sender.getUsername(),
            LocalDateTime.now(),false, receiver, sender, conversation.getId(), NotificationType.MESSAGE));
    }
    public void saveFriendRequestNotification(FriendsRequest friendsRequest, User receiver, User sender) {
        notificationsRepository.save(new Notification("Otrzymałeś zaproszenie do znajomych od " + sender.getUsername(),
                LocalDateTime.now(),false, receiver, sender, friendsRequest.getId(), NotificationType.FRIENDREQUEST));
    }

    public void saveInviteToMatchNotification(Invite invite, User receiver, User sender) {
        notificationsRepository.save(new Notification("Otrzymałeś zaproszenie do meczu od " + sender.getUsername(),
                LocalDateTime.now(),false, receiver, sender, invite.getId(), NotificationType.MATCHREQUEST));
    }

    public void saveUserJoinYourTeamNotification(Match match, User receiver, User sender){
        notificationsRepository.save(new Notification("Użytkwonik dołączył do twojego meczu " + sender.getUsername(),
                LocalDateTime.now(),false, receiver, sender, match.getId(), NotificationType.USERJOINTEAM));
    }
    public void saveUserHasBeenBannedOnPfNotification(Ban ban, User receiver, User sender) {
        notificationsRepository.save(new Notification("Zostałeś zbanowany na orliku " + ban.getPlayingField().getName(),
                LocalDateTime.now(),false, receiver, sender, ban.getId(), NotificationType.BAN));
    }
}
