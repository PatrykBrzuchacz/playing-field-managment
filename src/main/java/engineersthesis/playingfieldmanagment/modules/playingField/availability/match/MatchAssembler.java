package engineersthesis.playingfieldmanagment.modules.playingField.availability.match;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.ban.BanRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.web.dto.MatchDto;
import engineersthesis.playingfieldmanagment.web.dto.MatchWithLocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class MatchAssembler {

    @Autowired
    private BanRepository banRepository;

    public Page<MatchWithLocationDto> toDtoList(Page<Match> matches, User user, Pageable pageable) {
        AtomicReference<Long> totalElements = new AtomicReference<>(matches.getTotalElements());

        return new PageImpl<>(matches.stream().map(val -> {
            if (!(val.getMatchFromDate().equals(LocalDate.now()) && val.getMatchToTime().isBefore(LocalTime.now()))) {
                return toDto(val, user);
            }
            totalElements.updateAndGet(v -> v - 1);

            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList()), pageable,totalElements.get());
    }

    public MatchWithLocationDto toDto(Match match, User user) {
        if (match != null) {
            PlayingField playingField = match.getPlayingFieldAvailability().getPlayingField();

            final int[] size = new int[1];
            match.getTeams().forEach(it -> {
                size[0] = size[0] + it.getPlayers().size();
            });

            Boolean editable = true;
            if((match.getMatchFromDate().equals(LocalDate.now()) && match.getMatchFromTime().isBefore(LocalTime.now())) || match.getMatchFromDate().isBefore(LocalDate.now())) {
                editable =false;
            }
if(user!=null) {
    return new MatchWithLocationDto(match.getId(), LocalDateTime.of(match.getMatchFromDate(),
            match.getMatchFromTime()),
            LocalDateTime.of(match.getMatchFromDate(), match.getMatchToTime()),
            playingField.getAddress(), playingField.getName(), playingField.getLat(), playingField.getLng(),
            size[0],
            match.getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize() * 2,
            match.getIsBooked(), match.getIsPrivate(), match.getUser() != null ? match.getUser().getId() : 0,
            match.getUser() != null ? match.getUser().getUsername() : null,
            banRepository.existsByPlayingField_IdAndUser_Id(match.getPlayingFieldAvailability().getPlayingField().getId(), user.getId()),editable, match.getCode(), playingField.getRating(),
            playingField.getId());
} else {
    return new MatchWithLocationDto(match.getId(), LocalDateTime.of(match.getMatchFromDate(),
            match.getMatchFromTime()),
            LocalDateTime.of(match.getMatchFromDate(), match.getMatchToTime()),
            playingField.getAddress(), playingField.getName(), playingField.getLat(), playingField.getLng(),
            size[0],
            match.getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize() * 2,
            match.getIsBooked(), match.getIsPrivate(), match.getUser() != null ? match.getUser().getId() : 0,
            match.getUser() != null ? match.getUser().getUsername() : null,
            false,editable, match.getCode(), playingField.getRating(),playingField.getId());
}
} else return null;
    }

    public Page<MatchDto> toDtoListMatchesDto(Page<Match> matches, Pageable pageable) {
        AtomicReference<Long> totalElements = new AtomicReference<>(matches.getTotalElements());
        return new PageImpl<>(matches.stream().map(val -> {
            if (!(val.getMatchFromDate().equals(LocalDate.now()) && val.getMatchToTime().isBefore(LocalTime.now()))) {
                return toDtoMatchDto(val);
            }
            totalElements.updateAndGet(v -> v - 1);
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList()),pageable, totalElements.get());
    }

    public List<MatchDto> toDtoListMatchesDto(List<Match> matches) {
        return matches.stream().map(val -> {
            if (!(val.getMatchFromDate().equals(LocalDate.now()) && val.getMatchToTime().isBefore(LocalTime.now()))) {
                return toDtoMatchDto(val);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private MatchDto toDtoMatchDto(Match match) {
        if (match != null) {
            final int[] size = new int[1];
            match.getTeams().forEach(it -> {
                size[0] = size[0] + it.getPlayers().size();
            });
Boolean editable = true;
if((match.getMatchFromDate().equals(LocalDate.now()) && match.getMatchFromTime().isBefore(LocalTime.now())) || match.getMatchFromDate().isBefore(LocalDate.now())) {
    editable =false;
}
            return new MatchDto(match.getId(), LocalDateTime.of(match.getMatchFromDate(),
                    match.getMatchFromTime()),
                    LocalDateTime.of(match.getMatchFromDate(), match.getMatchToTime()), match.getIsBooked(),
                    size[0],
                    match.getPlayingFieldAvailability().getPlayingField().getPlayingFieldSetup().getTeamSize() * 2,
                    match.getIsPrivate(), match.getUser() != null ? match.getUser().getId() : null,
                    match.getUser() != null ?
                    match.getUser().getUsername() : null, editable, match.getCode(), match.getIsCodeFilled(),
                    match.getMatchContact() != null ?   match.getMatchContact().getPhoneNumber(): null,
                    match.getMatchContact() != null ? match.getMatchContact().getSurname():null,
                    match.getMatchContact() != null ?    match.getMatchContact().getEmail():null);
        } else return null;
    }

}
