package engineersthesis.playingfieldmanagment.modules.playingField.availability.ban;

import engineersthesis.playingfieldmanagment.web.dto.BanDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BanAssembler {

    public List<BanDto> toDtoList(List<Ban> bans) {
        return bans.stream().map(this::toDto).collect(Collectors.toList());
    }

    protected BanDto toDto(Ban ban) {
        return new BanDto(ban.getId(), ban.getPlayingField().getId(), ban.getUser().getId(),
                ban.getUser().getUsername());
    }
}
