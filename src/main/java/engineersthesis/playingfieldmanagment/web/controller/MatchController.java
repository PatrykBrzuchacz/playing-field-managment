package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.match.MatchService;
import engineersthesis.playingfieldmanagment.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MatchController {

    @Autowired
    private MatchService matchService;

//    @GetMapping("/playingField/{id}/matches")
//    public List<MatchDto> getMatchesByPf(@PathVariable("id") Long id, Pageable pageable) {
//        return matchService.getMatches(id, pageable);
//    }

    @PostMapping("/playingField/{id}/matches/search")
    public Page<MatchDto> getMatchesByPfAndSearch(@PathVariable("id") Long id,
                                                  @RequestBody SearchDto searchDto, Pageable pageable) {
        return matchService.getMatchesBySearchDto(id, searchDto, pageable);
    }

    @PostMapping("/matches/getByLocation")
    public Page<MatchWithLocationDto> getMatchesByPosition(@RequestBody SearchParams searchParams, Pageable pageable) {
        return matchService.getMatchesByLocation(searchParams, pageable);
    }

    @PutMapping("/matches/{id}/book")
    public CodeDto bookPlayingField(@PathVariable("id") Long matchId, @RequestBody ReservationDto reservationDto) {
        return matchService.bookPlayingField(matchId, reservationDto);
    }

    @PutMapping("/matches/{id}/unbook")
    public ResponseEntity<?> unbookPlayingField(@PathVariable("id") Long matchId) {
        matchService.unbookPlayingField(matchId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/matches/{id}")
    public ResponseEntity<?> deleteMatch(@PathVariable("id") Long id) {
        matchService.deleteMatch(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/matches/users/{id}/getAllMatches")
    public Page<MatchWithLocationDto> getAllMatchesByUserId(@PathVariable("id") Long id, Pageable pageable) {
        return matchService.getAllMatchesByUserId(id, pageable);
    }

    @GetMapping("/matches/userss/{id}/getAllMatchesAndReceiverNot")
    public Page<MatchWithLocationDto> getAllMatchesByUserIdAndReceiverNot(@PathVariable("id") Long id,
                                                                        Pageable pageable) {
        return matchService.getAllMatchesByUserIdAndReceiverNot(id, pageable);
    }
    @GetMapping("/matches/{id}")
    public MatchWithLocationDto getMatchByUserId(@PathVariable("id") Long id) {
        return matchService.getMatchById(id);
    }

    @PutMapping("/matches/{id}/fillCode")
    public void fillCode(@PathVariable("id") Long id) {
         matchService.fillCode(id);
    }

    @PutMapping("/matches/fillCode")
    public void fillCodeByCode(@RequestParam("code") String code) {
        matchService.fillCodeByCode(code);
    }
}
