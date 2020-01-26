package engineersthesis.playingfieldmanagment.modules.playingField.availability.match;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findAllByPlayingFieldAvailability_PlayingField_Id(Long id, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqual(double v, double v1, double v2, double v3m, LocalDate matchFromDate, LocalDate MatchToDate, LocalTime matchFromTime, LocalTime matchToTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalse(double v, double v1, double v2, double v3m, LocalDate matchFromDate, LocalDate MatchToDate, LocalTime matchFromTime, LocalTime matchToTime, Pageable pageable);

    boolean existsByUserAndMatchFromDateAndMatchFromTimeLessThanAndMatchToTimeGreaterThan(User user,
                                                                                          LocalDate matchToDate, LocalTime matchToTime, LocalTime matchFromTime);
    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqual(Long id, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqual(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalse(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalse(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalse(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalse(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalse(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalse(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalse(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndIsPrivateFalse(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndIsPrivateFalseAndUserIsNotNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNotNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndIsPrivateFalseAndUserIsNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNotNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_LatBetweenAndPlayingFieldAvailability_PlayingField_LngBetweenAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNotNull(double v, double v1, double v2, double v3, LocalDate parse, LocalDate parse1, LocalTime parse2, LocalTime parse3, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNotNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNotNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNotNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNotNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNotNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndIsFullFalseAndUserIsNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNotNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsFullFalseAndUserIsNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNotNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromDateLessThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndIsPrivateFalseAndUserIsNull(Long id, LocalDate fromDate, LocalDate parse, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNotNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    Page<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndMatchFromDateGreaterThanEqualAndMatchFromTimeGreaterThanEqualAndMatchToTimeLessThanEqualAndUserIsNull(Long id, LocalDate fromDate, LocalTime fromTime, LocalTime toTime, Pageable pageable);

    List<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndUser_Id(Long playingFieldId, Long userId);


    Match findByCode(String code);

    List<Match> findAllByPlayingFieldAvailability_PlayingField_IdAndBookedDateLessThanEqualAndIsCodeFilledAndCodeNotNull(Long id, LocalDateTime now, boolean b);

    Page<Match> findAllByTeams_Players_IdAndMatchFromDateGreaterThanEqual(Long id, Pageable pageable, LocalDate now);

    Page<Match> findAllByTeams_Players_IdAndTeams_Players_IdNotAndMatchFromDateGreaterThanEqual(Long id,
                                                                                                Long receiverId,
                                                                                              LocalDate now,   Pageable pageable);

    Page<Match> findAllByTeams_PlayersAndTeams_PlayersNotAndMatchFromDateGreaterThanEqual(User loggedUser, User one,
                                                                                          LocalDate now, Pageable pageable);

    boolean existsByTeams_Players_Id(Long id);
}

