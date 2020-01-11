package engineersthesis.playingfieldmanagment.modules.playingField.availability;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchAssembler;
import engineersthesis.playingfieldmanagment.web.dto.AvailabilityWithMatchesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayingFieldAvailabilityAssembler {

    @Autowired
    private MatchAssembler matchAssembler;

    public List<AvailabilityWithMatchesDto> toDtoList(List<PlayingFieldAvailability> playingFieldAvailabilities) {
        return playingFieldAvailabilities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public AvailabilityWithMatchesDto toDto(PlayingFieldAvailability playingFieldAvailability) {
        return new AvailabilityWithMatchesDto(playingFieldAvailability.getId(),
                LocalDateTime.of(playingFieldAvailability.getOpenDatePF(), playingFieldAvailability.getOpenTimePF()),
                LocalDateTime.of(playingFieldAvailability.getCloseDatePF(),
                        playingFieldAvailability.getCloseTimePF()),
                matchAssembler.toDtoListMatchesDto(playingFieldAvailability.getMatches()));
    }


}
