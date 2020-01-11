package engineersthesis.playingfieldmanagment.modules.infrastructure.notifications;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.invite.InviteRepository;
import engineersthesis.playingfieldmanagment.web.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationAssembler {

    @Autowired
    private InviteRepository inviteRepository;
    List<NotificationDto> toDtoList(List<Notification> notifications) {
        return notifications.stream().map(val -> {
            if(val.getNotificationType()==NotificationType.MATCHREQUEST
//                    || val.getNotificationType() ==
//                    NotificationType.USERJOINTEAM
            ) {
                return toDtoExternal(val, inviteRepository.getOne(val.getEntityId()).getMatch().getId());
            } else return toDto(val);
        }).collect(Collectors.toList());
    }
    NotificationDto toDto(Notification notification) {
        return new NotificationDto(notification.getId(),notification.getDateAndTime(),notification.getTitle(),
                notification.getSender().getId(),notification.getSender().getUsername(),
                notification.getEntityId(), notification.getNotificationType(), notification.isDisplayed(),
                notification.getAccepted());
    }

    NotificationDto toDtoExternal(Notification notification, Long externalId) {
        return new NotificationDto(notification.getId(),notification.getDateAndTime(),notification.getTitle(),
                notification.getSender().getId(),notification.getSender().getUsername(),
                notification.getEntityId(), notification.getNotificationType(), notification.isDisplayed(),
                notification.getAccepted(), externalId);
    }
}
