package engineersthesis.playingfieldmanagment.modules.playingField.availability.invite;

import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.NotificationService;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchAssembler;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.Status;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.web.dto.InviteDto;
import engineersthesis.playingfieldmanagment.web.dto.InviteWithUserDto;
import engineersthesis.playingfieldmanagment.web.dto.MatchWithLocationDto;
import engineersthesis.playingfieldmanagment.web.dto.UserUsernameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InviteService {
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private InviteAssembler inviteAssembler;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchAssembler matchAssembler;
    @Autowired
    private NotificationService notificationService;

    public Long sendInvite(Long matchId, Long userId) {
        User loggedUser = securityUserHelper.getLoggedUser();
        User user = userRepository.getOne(userId);
        if (!inviteRepository.existsBySenderAndReceiver_IdAndStatus(loggedUser, userId, Status.SENDED) || !inviteRepository.existsBySenderAndReceiver_IdAndStatus(loggedUser, userId, Status.ACCEPTED)) {
            Invite invite = inviteRepository.save(new Invite(loggedUser, user,
                    matchRepository.getOne(matchId),
                    Status.SENDED));
            notificationService.saveInviteToMatchNotification(invite,user,loggedUser);
            return invite.getId();
        } else throw new RuntimeException("User is actually invited to this match");
    }

    public Page<InviteDto> getLoggedUserInvites(Pageable pageable) {
        User loggedUser = securityUserHelper.getLoggedUser();
        Page<Invite> invites =
                inviteRepository.findAllByReceiverAndStatusAndMatch_MatchFromDateGreaterThanEqual(loggedUser,
                        Status.SENDED, LocalDate.now(), pageable);

        return inviteAssembler.toDtoList(invites, pageable);
    }

    @Transactional
    public MatchWithLocationDto acceptInvite(Long id) {
        Invite invite = inviteRepository.getOne(id);
        invite.setStatus(Status.ACCEPTED);
        User loggedUser = securityUserHelper.getLoggedUser();
        if (invite.getReceiver().getId() == loggedUser.getId()) {
            if (!invite.getMatch().getTeams().get(0).getPlayers().contains(loggedUser) || !invite.getMatch().getTeams().get(1).getPlayers().contains(loggedUser)) {
                if (!invite.getMatch().getTeams().get(0).getIsFull()) {
                    invite.getMatch().getTeams().get(0).getPlayers().add(loggedUser);
                } else if (!invite.getMatch().getTeams().get(1).getIsFull()) {
                    invite.getMatch().getTeams().get(1).getPlayers().add(loggedUser);
                } else {
                    throw new RuntimeException("Both teams are full");
                }
                return matchAssembler.toDto(invite.getMatch(), loggedUser);
            } else throw new RuntimeException("User is arleady in this match");
        } else throw new RuntimeException("Invite is not for you!");

    }

    @Transactional
    public void declineInvite(Long id) {
        Invite invite = inviteRepository.getOne(id);
        User loggedUser = securityUserHelper.getLoggedUser();
        if (invite.getMatch().getUser().getId().equals(loggedUser.getId()) || invite.getReceiver().getId() == loggedUser.getId()) {
            invite.setStatus(Status.DECLINED);
        }
    }

    public List<InviteWithUserDto> getMatchInvites(Long id) {
        return inviteRepository.findAllByMatchIdAndStatus(id, Status.SENDED).stream().map(this::toDto).collect(Collectors.toList());
    }

    private InviteWithUserDto toDto(Invite invite) {
        return new InviteWithUserDto(invite.getId(), new UserUsernameDto(invite.getReceiver().getUsername(),
                invite.getReceiver().getId(),
                invite.getReceiver().getPosition()));
    }

    public List<Long> getUserInvitesId(Long id) {
        List<Invite> invites = inviteRepository.findAllByReceiver_IdAndStatus(id, Status.SENDED);
        return invites.stream().map(Invite::getMatch).collect(Collectors.toList()).stream().map(Match::getId).collect(Collectors.toList());
    }
}
