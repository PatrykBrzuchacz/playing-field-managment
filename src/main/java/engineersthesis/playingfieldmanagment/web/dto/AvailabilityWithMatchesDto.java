package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AvailabilityWithMatchesDto {
    private Long availabilityId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private List<MatchDto> matchesDto;
}
