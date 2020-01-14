package engineersthesis.playingfieldmanagment.modules.playingField.availability;


import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.Match;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.web.dto.AvailabilityWithMatchesDto;
import engineersthesis.playingfieldmanagment.web.dto.MatchDto;
import engineersthesis.playingfieldmanagment.web.dto.PFAvailabilityDto;
import engineersthesis.playingfieldmanagment.web.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;


@Service
public class PlayingFieldAvailabilityService {

    @Autowired
    private PlayingFieldAvailabilityRepository playingFieldAvailabilityRepository;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private PlayingFieldAvailabilityAssembler playingFieldAvailabilityAssembler;

    public List<AvailabilityWithMatchesDto> getAvailabilities(Long id, Boolean showAll) {
        List<PlayingFieldAvailability> playingFieldAvailabilities;
        if (showAll) {
            playingFieldAvailabilities = playingFieldRepository.getOne(id).getPlayingFieldAvailabilities();
        } else {
            playingFieldAvailabilities =
                    playingFieldRepository.getOne(id).getPlayingFieldAvailabilities().stream().filter(it -> (it.getCloseDatePF().isAfter(LocalDate.now()) || it.getCloseDatePF().isEqual(LocalDate.now()))).collect(Collectors.toList());
        }
//        return playingFieldAvailabilityAssembler.toDtoList(playingFieldAvailabilities)
//                .stream().sorted(Comparator.comparing(AvailabilityWithMatchesDto::getFromDate)).collect(Collectors.toList());
        List<AvailabilityWithMatchesDto>  dto =
                playingFieldAvailabilityAssembler.toDtoList(playingFieldAvailabilities);
        dto.sort(Comparator.comparing(AvailabilityWithMatchesDto::getFromDate));
        dto.forEach(val-> val.getMatchesDto().sort(Comparator.comparing(MatchDto::getMatchFrom)));
//               .stream().sorted(Comparator.comparing(AvailabilityWithMatchesDto::getFromDate)).collect(Collectors
//               .toList());
        return dto;
    }

    @Transactional
    public AvailabilityWithMatchesDto setPFAvailability(Long id, PFAvailabilityDto pfAvailabilityDto) {
        if (!playingFieldRepository.getOne(id).getUser().getId().equals(securityUserHelper.getLoggedUser().getId())) {
            throw new UserIsNotOwnerException();
        }
        checkTimeAndDataCorrectness(pfAvailabilityDto);
        checkDatesOverlap(pfAvailabilityDto, id, 0L);
        PlayingFieldAvailability playingFieldAvailability = createPFAvailabilityFromDto(pfAvailabilityDto, id);

        LocalDate openDate = pfAvailabilityDto.getOpenDatePF();
        LocalDate closeDate = pfAvailabilityDto.getCloseDatePF();
        LocalTime openTime = pfAvailabilityDto.getOpenTimePF();
        LocalTime closeTime = pfAvailabilityDto.getCloseTimePF();
        LocalTime startTime = openTime;

        while (true) {
            if (openDate.isBefore(closeDate) || openDate.equals(closeDate)) {
                if (openTime.plusMinutes(pfAvailabilityDto.getMatchTime()).isAfter(closeTime)) {

                    if (openTime.plusMinutes(30).isBefore(closeTime)) {
                        createLastMatch(openDate, openTime, closeTime,
                                playingFieldAvailability);  //ChronoUnit.MINUTES.between(i,
                        // pfAvailability.getClosedPF())
                    }

                    if (openDate.isBefore(closeDate)) {
                        openDate = openDate.plusDays(1);
                        openTime = startTime;
                    } else break;


                } else {
                    createMatch(openDate, openTime, pfAvailabilityDto.getMatchTime(),
                            playingFieldAvailability);
                    openTime = openTime.plusMinutes(pfAvailabilityDto.getMatchTime());
                }
            } else break;
        }
        playingFieldAvailabilityRepository.save(playingFieldAvailability);
        return playingFieldAvailabilityAssembler.toDto(playingFieldAvailability);
    }


    private void checkTimeAndDataCorrectness(PFAvailabilityDto pfAvailabilityDto) {
        if (pfAvailabilityDto.getOpenDatePF().isAfter(pfAvailabilityDto.getCloseDatePF()) ||
                pfAvailabilityDto.getOpenTimePF().isAfter(pfAvailabilityDto.getCloseTimePF())) {
            throw new WrongReceivedTimesException();
        }
        if (pfAvailabilityDto.getOpenDatePF().isBefore(LocalDate.now()) || (pfAvailabilityDto.getOpenTimePF().isBefore(LocalTime.now()) &&
                pfAvailabilityDto.getOpenDatePF().equals(LocalDate.now()))) {
            throw new DateCannotBeBeforeCurrentDateException();
        }
        if (MINUTES.between(pfAvailabilityDto.getOpenTimePF(), pfAvailabilityDto.getCloseTimePF()) < pfAvailabilityDto.getMatchTime()) {
            throw new DatesDontHaveEnoughTimeForSingleMatchException();
        }
    }

    private PlayingFieldAvailability createPFAvailabilityFromDto(PFAvailabilityDto pfAvailabilityDto, Long id) {
        return new PlayingFieldAvailability(pfAvailabilityDto.getOpenDatePF(),
                pfAvailabilityDto.getCloseDatePF(), pfAvailabilityDto.getOpenTimePF(),
                pfAvailabilityDto.getCloseTimePF(), pfAvailabilityDto.getMatchTime(),
                playingFieldRepository.getOne(id));
    }

    private void createMatch(LocalDate startDate, LocalTime startTime, int matchTime,
                             PlayingFieldAvailability playingFieldAvailability) {
        playingFieldAvailability.addMatch((new Match(startDate, startTime,
                startTime.plusMinutes(matchTime),
                playingFieldAvailability)));
    }


    private void createLastMatch(LocalDate openDate, LocalTime startTime, LocalTime endTime,
                                 PlayingFieldAvailability playingFieldAvailability) {
        playingFieldAvailability.addMatch(new Match(openDate, startTime, endTime, playingFieldAvailability
        ));
    }


    private void checkDatesOverlap(PFAvailabilityDto pfAvailabilityDto, Long playingFieldId, Long playingFieldAvailabilityId) {
        PlayingField playingField = playingFieldRepository.getOne(playingFieldId);
        PlayingFieldAvailability playingFieldAvailability = new PlayingFieldAvailability();

        if (playingFieldAvailabilityId != 0) {
            playingFieldAvailability = playingFieldAvailabilityRepository.getOne(playingFieldAvailabilityId);
        }

        LocalDate openDate = pfAvailabilityDto.getOpenDatePF();
        LocalDate closeDate = pfAvailabilityDto.getCloseDatePF();
        LocalTime openTime = pfAvailabilityDto.getOpenTimePF();
        LocalTime closeTime = pfAvailabilityDto.getCloseTimePF();


        for (PlayingFieldAvailability pfa : playingField.getPlayingFieldAvailabilities()) {
            if (playingFieldAvailability != pfa) {

                if (!((openDate.isBefore(pfa.getOpenDatePF()) && closeDate.isBefore(pfa.getOpenDatePF())) ||
                        (openDate.isAfter(pfa.getCloseDatePF()) && closeDate.isAfter(pfa.getCloseDatePF())))) {

                    if (!((openTime.isBefore(pfa.getOpenTimePF()) && closeTime.isBefore(pfa.getOpenTimePF())) ||
                            (closeTime.equals(pfa.getOpenTimePF()) && openTime.isBefore(pfa.getOpenTimePF())) ||
                            (openTime.equals(pfa.getCloseTimePF()) && closeTime.isAfter(pfa.getCloseTimePF())) ||
                            (openTime.equals(pfa.getOpenTimePF()) && closeTime.equals(pfa.getOpenTimePF())) ||
                            (openTime.equals(pfa.getCloseTimePF()) && closeTime.equals(pfa.getCloseTimePF())) ||
                            (openTime.isAfter(pfa.getCloseTimePF()) && closeTime.isAfter(pfa.getCloseTimePF())))) {
                        throw new DatesOverlapException();
                    }
                }
            }
        }
    }

    public void deletePfAvailability(Long id) {
        PlayingFieldAvailability playingFieldAvailability = playingFieldAvailabilityRepository.getOne(id);
        playingFieldAvailability.getMatches().forEach(match -> {

            if (match.getIsBooked() && match.getMatchFromDate().minusDays(3).isBefore(LocalDate.now())) {
                throw new RuntimeException("You cannot delete playing field availability, its to late");
            }
        });
        playingFieldAvailabilityRepository.delete(playingFieldAvailability);

    }


}


// todo update tylko do skrocenia/wydluzenia meczu jesli jest to mozliwe i otwarcie/close, maybe make pfuUpdateHelper
//    @Transactional
//    public void updatePFAvailability(Long playingFieldId, Long playingFieldAvailabilityId, PFAvailabilityDto pfAvailabilityDto) {
//        checkTimeAndDataCorrectness(pfAvailabilityDto);
//        //dorobic z wyjatkiem tych aktualnych
//        PlayingFieldAvailability pfa = playingFieldAvailabilityRepository.getOne(playingFieldAvailabilityId);
//
//        LocalDate openDate = pfAvailabilityDto.getOpenDatePF();
//        LocalDate closeDate = pfAvailabilityDto.getCloseDatePF();
//        LocalTime openTime = pfAvailabilityDto.getOpenTimePF();
//        LocalTime closeTime = pfAvailabilityDto.getCloseTimePF();
//        LocalTime startTime = openTime;
//        Boolean isEnded = false;
//            checkDatesOverlap(pfAvailabilityDto, playingFieldId, playingFieldAvailabilityId);
//        List<Match> toDelete = new ArrayList<>();
//            for(Match match: pfa.getMatches()) {
//                if(match.getUserDto() != null) {
//                    throw new CannotUpdatePFAvailabilityBecauseIsInUsedException();
//                }
//                if (openTime.plusMinutes(pfAvailabilityDto.getMatchTime()).isAfter(closeTime)) {
//
//                    if (openTime.plusMinutes(30).isBefore(closeTime)) {
//                        match.setMatchFrom(LocalDateTime.of(openDate, openTime));
//                        match.setMatchTo(LocalDateTime.of(openDate, closeTime));
//                        openTime = openTime.plusMinutes(999999);
//                    }
//
//                    if (openDate.isBefore(closeDate)) {
//                        openDate = openDate.plusDays(1);
//                        openTime = startTime;
//                    } else {
//                        if (!openTime.plusMinutes(30).isBefore(closeTime)) {
//                            System.out.println("asdqwe");
//                            toDelete.add(match);
//                            isEnded = true;
//                        }
//                        }
//                }
//                    else {
//                    match.setMatchFrom(LocalDateTime.of(openDate, openTime));
//                    match.setMatchTo(LocalDateTime.of(openDate, openTime).plusMinutes(pfAvailabilityDto.getMatchTime()));
//                        openTime = openTime.plusMinutes(pfAvailabilityDto.getMatchTime());
//                    }
//            }
//            pfa.getMatches().removeAll(toDelete);
//if(!isEnded) {
//        while (true) {
//            if(openDate.isBefore(closeDate) || openDate.equals(closeDate)){
//                if (openTime.plusMinutes(pfAvailabilityDto.getMatchTime()).isAfter(closeTime)) {
//
//                    if (openTime.plusMinutes(30).isBefore(closeTime)) {
//                        createLastMatch(LocalDateTime.of(openDate, openTime), LocalDateTime.of(openDate, closeTime), pfa);  //ChronoUnit.MINUTES.between(i, pfAvailability.getClosedPF())
//                    }
//
//                    if (openDate.isBefore(closeDate)) {
//                        openDate = openDate.plusDays(1);
//                        openTime = startTime;
//                    } else {
//                        break;
//                    }
//
//                } else {
//                    createMatch(LocalDateTime.of(openDate, openTime), pfAvailabilityDto.getMatchTime(), pfa);
//                    openTime = openTime.plusMinutes(pfAvailabilityDto.getMatchTime());
//                }}
//            else break;
//        }
//    }}

//            if (playingFieldAvailabilityRepository.existsByPlayingFieldAndOpenFullPFAfterAndCloseFullBefore(playingField,
//                    LocalDateTime.of(closeDate, closeTime), LocalDateTime.of(openDate, openTime)))
//                throw new DatesOverlapException();
//        }


//
//    public void checkDatesOverlap(PFAvailabilityDto pfAvailabilityDto, Long senderId) {
////            if (playingFieldAvailabilityRepository.existsByPlayingFieldIdByDatesAndTimesBetween(senderId, pfAvailabilityDto.getOpenDatePF(), pfAvailabilityDto.getCloseDatePF(),
////                    pfAvailabilityDto.getOpenTimePF(), pfAvailabilityDto.getCloseTimePF()) != null) {
//
//        PlayingField playingField = playingFieldRepository.getOne(senderId);
//        LocalDate openDate = pfAvailabilityDto.getOpenDatePF();
//        LocalDate closeDate = pfAvailabilityDto.getCloseDatePF();
//        LocalTime openTime = pfAvailabilityDto.getOpenTimePF();
//        LocalTime closeTime = pfAvailabilityDto.getCloseTimePF();
//
//        for (PlayingFieldAvailability pfa : playingField.getMatches()) {
//            if ((openDate.isAfter(pfa.getOpenDatePF()) && openDate.isBefore(pfa.getCloseDatePF()))
//                    || openDate.equals(pfa.getOpenDatePF())
//                    || (closeDate.isAfter(pfa.getOpenDatePF()) && closeDate.isBefore(pfa.getCloseDatePF()))
//                    || closeDate.equals(pfa.getCloseDatePF())
//                    || openDate.isBefore(pfAvailabilityDto.getOpenDatePF()) &&
//                    ((closeDate.isBefore(pfAvailabilityDto.getCloseDatePF()) && closeDate.isAfter(pfAvailabilityDto.getOpenDatePF()))) ||
//                    closeDate.isAfter(pfAvailabilityDto.getCloseDatePF())
//                    || closeDate.isAfter(pfAvailabilityDto.getCloseDatePF()) &&
//                    ((openDate.isBefore(pfAvailabilityDto.getCloseDatePF()) && openDate.isAfter(pfAvailabilityDto.getOpenDatePF())))
//                    || openDate.isBefore((pfAvailabilityDto.getOpenDatePF()))){
//
////                if(openTime.isAfter(pfa.getOpenTimePF()) && openTime.isBefore(pfa.getCloseTimePF())
////                        || openTime.equals(pfa.getCloseTimePF())
////                        || ) {
//                    throw new DatesOverlapException();
//            }
//        }
//    }
//}
//
//        }
//
//        @Transactional
//        public void updatePFAvailability(Long senderId, PFAvailabilityDto pfAvailabilityDto) {
//            checkDatesOverlap(pfAvailabilityDto, senderId);
//            PlayingFieldAvailability pfAvailability = playingFieldAvailabilityRepository.getOne(senderId);
//            pfAvailability.setMatches(new ArrayList<>());
//            LocalDateTime i = pfAvailability.getOpenedPF();
//
//            while(true){
//                if(i.plusMinutes(pfAvailability.getMatchTime()).isAfter(pfAvailability.getClosedPF())){
//                    if(i.plusMinutes(30).isBefore(pfAvailability.getClosedPF())){ // if we want to have last match for full res ttime - if(i.plusMinutes(pfAvailability.getMatchTime()).plusMinutes(30).isBefore(pfAvailability.getClosedPF())){
//                        createLastMatch(i, pfAvailability);  //ChronoUnit.MINUTES.between(i, pfAvailability.getClosedPF())
//                    }
//                    break;
//                }
//                createMatch(i, pfAvailability);
//                i=i.plusMinutes(pfAvailability.getMatchTime());
//            }
//        }




