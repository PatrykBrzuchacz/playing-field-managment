package engineersthesis.playingfieldmanagment.modules.playingField.availability.match;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.contact.MatchContact;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.reservation.Reservation;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.reservation.ReservationRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.team.Team;
import engineersthesis.playingfieldmanagment.modules.team.TeamRepository;
import engineersthesis.playingfieldmanagment.web.dto.*;
import engineersthesis.playingfieldmanagment.web.exception.CodeExistsException;
import engineersthesis.playingfieldmanagment.web.exception.UserHaveArleadyBookedPFInThisTimeException;
import engineersthesis.playingfieldmanagment.web.exception.UserIsNotOwnerException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchAssembler matchAssembler;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    public Page<MatchWithLocationDto> getMatchesByLocation(SearchParams searchParams, Pageable pageable) {
        Page<Match> matches;
        SearchDto searchDto = searchParams.getSearchDto();

        if (searchDto.isShowFull() && searchDto.isShowPrivate()) {
            if (searchDto.getReserved() == 1) {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqual(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            } else if (searchDto.getReserved() == 2) {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNotNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            } else {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            }

        } else if (!searchDto.isShowFull() && !searchDto.isShowPrivate()) {
            if (searchDto.getReserved() == 1) {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndIsPrivateFalse(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);

            } else if (searchDto.getReserved() == 2) {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndIsPrivateFalseAndUserIsNotNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);

            } else {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndIsPrivateFalseAndUserIsNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            }
        } else if (!searchDto.isShowFull()) {
            if (searchDto.getReserved() == 1) {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalse(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            } else if (searchDto.getReserved() == 2) {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNotNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);

            } else {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            }

        } else {
            if (searchDto.getReserved() == 1) {

                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalse(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            } else if (searchDto.getReserved() == 2) {
                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNotNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);

            } else {

                matches =
                        matchRepository.findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNull(
                                searchParams.getLat() - searchParams.getRangeInKm() / 110,
                                searchParams.getLat() + searchParams.getRangeInKm() / 110,
                                searchParams.getLng() - searchParams.getRangeInKm() / 110,
                                searchParams.getLng() + searchParams.getRangeInKm() / 110,
                                LocalDate.parse(searchParams.getSearchDto().getFromDate()),
                                LocalDate.parse(searchParams.getSearchDto().getToDate()),
                                LocalTime.parse(searchParams.getSearchDto().getFromTime()),
                                LocalTime.parse(searchParams.getSearchDto().getToTime())
                                , pageable);
            }
        }

        return matchAssembler.toDtoList(matches, securityUserHelper.getLoggedUser(), pageable);
    }

//    public List<MatchDto> getMatches(Long id, Pageable pageable) {
//        return matchAssembler.toDtoListMatchesDto(matchRepository.findAllByPlayingFieldAvailability_PlayingField_Id(id, pageable));
//    }

    @Transactional
    public CodeDto bookPlayingField(Long availabilityId, ReservationDto reservationDto) {
        User user = securityUserHelper.getLoggedUser();
        Match matchToBook = matchRepository.getOne(availabilityId);

        if (matchRepository.existsByUserAndMatchFromDateAndMatchFromTimeLessThanAndMatchToTimeGreaterThan(
                user, matchToBook.getMatchFromDate(), matchToBook.getMatchToTime(),
                matchToBook.getMatchFromTime())) {
            throw new UserHaveArleadyBookedPFInThisTimeException();
        }
        Team team = new Team(matchToBook, "Drużyna czerwona");
        team.addPlayer(user);
        Reservation reservation = reservationRepository.save(new Reservation(reservationDto.getEmail(),
                reservationDto.getLastName(),
                reservationDto.getPhoneNumber(), matchToBook));
        matchToBook.setReservation(reservation);
        teamRepository.save(team);
        teamRepository.save(new Team(matchToBook, "Drużyna niebieska"));
        matchToBook.setIsPrivate(reservationDto.getIsPrivate());
        matchToBook.setIsBooked(true);
        matchToBook.setUser(user);
        matchToBook.setCode("PF-" + StringUtils.repeat("0",countDigits(matchToBook.getId())) + matchToBook.getId());
        matchToBook.setBookedDate(LocalDateTime.now());
        matchToBook.setMatchContact(new MatchContact(matchToBook,reservation.getPhoneNumber(),
                reservation.getEmail(),reservation.getLastName()));
        return new CodeDto(matchToBook.getCode());
    }

    private int countDigits(Long id){
        int count = 5;
        while(id != 0)
        {
            id /= 10;
            --count;
        }
        return count;
    }


    @Transactional
    public void unbookPlayingField(Long availabilityId) {
        Match matchToUnbook = matchRepository.getOne(availabilityId);
        User user = securityUserHelper.getLoggedUser();

        if (!user.getId().equals(matchToUnbook.getUser().getId())) {
            throw new UserIsNotOwnerException();
        }
        matchToUnbook.setUser(null);
        matchToUnbook.setReservation(null);
        matchToUnbook.setIsPrivate(false);
        matchToUnbook.setIsBooked(false);
        matchToUnbook.getTeams().forEach(Team::removeAllPlayers);
        matchToUnbook.setCode(null);
        matchToUnbook.setBookedDate(null);

        matchRepository.flush();
        matchToUnbook.resetTeams(matchToUnbook);
    }

    public void deleteMatch(Long matchId) {
        Match match = matchRepository.getOne(matchId);
        if (match.getPlayingFieldAvailability().getPlayingField().getUser() == securityUserHelper.getLoggedUser()) {
            if (match.getMatchFromDate().minusDays(3).isBefore(LocalDate.now()) && match.getIsBooked()) {
                throw new RuntimeException("You cannot delete match because its to late");
            }
            matchRepository.deleteById(matchId);
        } else {
            throw new RuntimeException("You are not owner of playing field");
        }
    }

    public Page<MatchDto> getMatchesBySearchDto(Long id, SearchDto searchDto, Pageable pageable) {
        LocalDate fromDate;
        LocalTime fromTime;
        LocalTime toTime;

        if (searchDto.getFromDate().equals("Invalid date")) {
            fromDate = LocalDate.now();
        } else {
            fromDate = LocalDate.parse(searchDto.getFromDate());
        }
        if (searchDto.getFromTime().equals("")) {
            fromTime = LocalTime.parse("00:00:00");
        } else {
            fromTime = LocalTime.parse(searchDto.getFromTime());
        }

        if (searchDto.getToTime().equals("")) {
            toTime = LocalTime.parse("23:59:59");
        } else {
            toTime = LocalTime.parse(searchDto.getToTime());
        }

        Page<Match> matches;
        if (searchDto.getToDate().equals("Invalid date")) {
            if (searchDto.isShowFull() && searchDto.isShowPrivate()) {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqual(
                                    id, fromDate, fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNotNull(
                                    id, fromDate, fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNull(
                                    id, fromDate, fromTime, toTime,pageable);
                }
            } else if (!searchDto.isShowFull() && !searchDto.isShowPrivate()) {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalse(
                                    id, fromDate, fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNotNull(
                                    id, fromDate, fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNull(
                                    id, fromDate, fromTime, toTime,pageable);
                }
            } else if (!searchDto.isShowFull()) {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalse(
                                    id, fromDate, fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNotNull(
                                    id, fromDate, fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNull(
                                    id, fromDate, fromTime, toTime,pageable);
                }

            } else {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalse(
                                    id, fromDate, fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNotNull(
                                    id, fromDate, fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNull(
                                    id, fromDate, fromTime, toTime,pageable);
                }
            }
        } else {
            if (searchDto.isShowFull() && searchDto.isShowPrivate()) {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqual(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNotNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                }

            } else if (!searchDto.isShowFull() && !searchDto.isShowPrivate()) {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalse(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNotNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                }

            } else if (!searchDto.isShowFull()) {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalse(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNotNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                }

            } else {
                if (searchDto.getReserved() == 1) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalse(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else if (searchDto.getReserved() == 2) {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNotNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                } else {
                    matches =
                            matchRepository.findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNull(
                                    id, fromDate, LocalDate.parse(searchDto.getToDate()), fromTime, toTime,pageable);
                }

            }
        }
        return matchAssembler.toDtoListMatchesDto(matches, pageable);
    }

    public Page<MatchWithLocationDto> getAllMatchesByUserId(Long id, Pageable pageable) {
        User loggedUser = securityUserHelper.getLoggedUser();

        return  matchAssembler.toDtoList(matchRepository.findAllByTeams_Players_IdAndMatchFromDateGreaterThanEqual(id,
                        pageable, LocalDate.now()), loggedUser, pageable);
    }

    @Transactional
    public void fillCode(Long id) {
        matchRepository.getOne(id).setIsCodeFilled(true);
    }

    @Transactional
    public void fillCodeByCode(String code) {
        Match match = matchRepository.findByCode(code);
        if(match!=null) {
            if(match.getIsCodeFilled()) {
            throw new CodeExistsException();
            }else {
            match.setIsCodeFilled(true);
        } }
        else {
            throw new RuntimeException("Match with this code doesnt exists in database");
        }
    }

    public MatchWithLocationDto getMatchById(Long id) {
        return matchAssembler.toDto(matchRepository.getOne(id), securityUserHelper.getLoggedUser());

    }

    public Page<MatchWithLocationDto> getAllMatchesByUserIdAndReceiverNot(Long id, Pageable pageable) {
        User loggedUser = securityUserHelper.getLoggedUser();

        return  matchAssembler.toDtoList(matchRepository.findAllByTeams_PlayersAndTeams_PlayersNotAndMatchFromDateGreaterThanEqual(loggedUser,userRepository.getOne(id),
                pageable, LocalDate.now()), loggedUser, pageable);

    }
}
