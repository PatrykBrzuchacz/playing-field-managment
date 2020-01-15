package engineersthesis.playingfieldmanagment.web.controller;


import engineersthesis.playingfieldmanagment.modules.playingField.PlayingFieldService;
import engineersthesis.playingfieldmanagment.modules.playingField.availability.PlayingFieldAvailabilityService;
import engineersthesis.playingfieldmanagment.web.dto.AvailabilityWithMatchesDto;
import engineersthesis.playingfieldmanagment.web.dto.MatchDto;
import engineersthesis.playingfieldmanagment.web.dto.PFAvailabilityDto;
import engineersthesis.playingfieldmanagment.web.dto.PlayingFieldSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PlayingFieldController {

    @Autowired
    private PlayingFieldAvailabilityService playingFieldAvailabilityService;
    @Autowired
    private PlayingFieldService playingFieldService;

    @PreAuthorize("hasRole('ROLE_WORKER')")
    @PostMapping("/worker/playingField/{id}/setAvailability")
    public AvailabilityWithMatchesDto setPFAvailability(@PathVariable("id") Long id, @RequestBody PFAvailabilityDto pfAvailabilityDto) {
        return playingFieldAvailabilityService.setPFAvailability(id, pfAvailabilityDto);
    }

    @PreAuthorize("hasRole('ROLE_WORKER')")
    @DeleteMapping("/worker/playingField/playingFieldAvailability/{id}")
    public List<MatchDto> deletePFAvailability(@PathVariable("id") Long id) {
        return playingFieldAvailabilityService.deletePfAvailability(id);
    }

    @GetMapping("/playingField/availabilities/{id}")
    public List<AvailabilityWithMatchesDto> getAvailabilities(@PathVariable("id") Long id,
                                                              @RequestParam("showAll") Boolean showAll) {
        return playingFieldAvailabilityService.getAvailabilities(id, showAll);
    }
    //out?

    @GetMapping("/playingField/{id}/setup")
    public PlayingFieldSetupDto getPFSetup(@PathVariable("id") Long id) {
        return playingFieldService.getPlayingFieldSetup(id);
    }

    @PreAuthorize("hasRole('ROLE_WORKER')")
    @PutMapping("/worker/playingField/{id}/setup")
    public void updatePFSetup(@PathVariable("id") Long id, @RequestParam("description") String description,
                              @RequestParam("teamSize") String teamSize,
                              @RequestParam("pfPhoto") MultipartFile pfPhoto,
                              @RequestParam("name") String name) throws IOException {
        playingFieldService.updatePFSetup(id, new PlayingFieldSetupDto(description, Integer.parseInt(teamSize),
                pfPhoto.getBytes(), name));
    }

    @PreAuthorize("hasRole('ROLE_WORKER')")
    @GetMapping("/playingField/{id}/setWarnings")
    public void setWarnings(@PathVariable("id") Long id) {
        playingFieldService.setWarnings(id);
    }

}

