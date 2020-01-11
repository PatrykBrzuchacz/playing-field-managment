package engineersthesis.playingfieldmanagment.modules.playingField.availability.invite;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchAssembler;
import engineersthesis.playingfieldmanagment.web.dto.InviteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InviteAssembler {
    @Autowired
    private MatchAssembler matchAssembler;

    public Page<InviteDto> toDtoList(Page<Invite> invites, Pageable pageable) {
        return  new PageImpl<>(invites.stream().map(val -> {
            if (!(val.getMatch().getMatchFromDate().equals(LocalDate.now()) && val.getMatch().getMatchToTime().isBefore(LocalTime.now()))) {
                return toDto(val);
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList()),pageable, invites.getTotalElements());
    }

    private InviteDto toDto(Invite invite) {
        return new InviteDto(invite.getId(),matchAssembler.toDto(invite.getMatch(), invite.getReceiver()),
                invite.getSender().getUsername(),
                invite.getSender().getId());
    }
}
