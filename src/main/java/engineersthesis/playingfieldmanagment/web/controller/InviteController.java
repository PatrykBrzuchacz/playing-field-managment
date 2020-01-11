package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.invite.InviteService;
import engineersthesis.playingfieldmanagment.web.dto.InviteDto;
import engineersthesis.playingfieldmanagment.web.dto.InviteWithUserDto;
import engineersthesis.playingfieldmanagment.web.dto.MatchWithLocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InviteController {
    @Autowired
    private InviteService inviteService;

    @PostMapping("/matches/{id}/invites/{userId}")
    public Long sendInvite(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        return inviteService.sendInvite(id, userId);
    }

    @GetMapping("/users/invites")
    public Page<InviteDto> getUserInvites(Pageable pageable) {
        return inviteService.getLoggedUserInvites(pageable);
    }

    @PutMapping("/invites/{id}/accept")
    public MatchWithLocationDto acceptInvite(@PathVariable("id") Long id) {
        return inviteService.acceptInvite(id);
    }

    @PutMapping("/invites/{id}/decline")
    public void declineInvite(@PathVariable("id") Long id) {
        inviteService.declineInvite(id);
    }

    @GetMapping("/matches/{id}/invites")
    public List<InviteWithUserDto> getMatchInvites(@PathVariable("id") Long id) {
        return inviteService.getMatchInvites(id);
    }

    @GetMapping("/users/{id}/invites")
    public List<Long> getUserInvitesId(@PathVariable("id") Long id) {
        return inviteService.getUserInvitesId(id);
    }
}
