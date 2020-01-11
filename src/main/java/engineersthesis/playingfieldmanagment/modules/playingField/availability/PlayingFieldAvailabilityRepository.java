package engineersthesis.playingfieldmanagment.modules.playingField.availability;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PlayingFieldAvailabilityRepository extends JpaRepository<PlayingFieldAvailability, Long> {

//    @Query("Select distinct pfa From PlayingFieldAvailability pfa where pfa.playingField.senderId = :senderId and  (:opened between pfa.openedPF and pfa.closedPF or :closed between pfa.openedPF and pfa.closedPF) " +
//            "or ((:opened > pfa.openedPF and :opened < pfa.closedPF) or(:closed > pfa.closedPF))")
//    @Query("Select pfa From (select pfa2 from PlayingFieldAvailable where :open between pfa2.openedPF and pfa2.closedPF) pfa where pfa.playingField.senderId = :senderId")

//    @Query("Select distinct pfa From PlayingFieldAvailability pfa where pfa.playingField.senderId = :senderId and :opened between pfa.openedPF and pfa.closedPF or :closed between pfa.openedPF and pfa.closedPF " +
//            "or :opened < pfa.openedPF or :closed > pfa.openedPF")
//    PlayingFieldAvailability existsByPlayingFieldIdAndOpenedPFBetween(Long senderId, LocalDateTime opened, LocalDateTime closed);
//    // na froncie dodac to tak, zeby sie inkrementowal suwak z minutami o tyle, co wpiszemy ze trwa mecz??

//    @Query("select pfa from PlayingFieldAvailability pfa where pfa.playingField.senderId = :senderId")
//    PlayingFieldAvailability existsByPlayingFieldIdByDatesAndTimesBetween(Long senderId, LocalDate openDate, LocalDate closeDate, LocalTime openTime, LocalTime closeTime);

//    boolean existsByPlayingFieldAndOpenFullPFAfterAndCloseFullBefore(PlayingField playingField, LocalDateTime to, LocalDateTime from);

    List<PlayingFieldAvailability> findAllByPlayingFieldAndOpenDatePFBeforeAndCloseDatePFAfterAndOpenTimePFBeforeAndCloseTimePFAfter(
            PlayingField playingField, LocalDate openDate, LocalDate closeDate, LocalTime openTime, LocalTime closeTime);

}
