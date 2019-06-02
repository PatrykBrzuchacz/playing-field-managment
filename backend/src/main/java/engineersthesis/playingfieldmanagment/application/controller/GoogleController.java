package engineersthesis.playingfieldmanagment.application.controller;

import engineersthesis.playingfieldmanagment.application.model.PlayingField;
import engineersthesis.playingfieldmanagment.application.model.dto.GoogleMapDto;
import engineersthesis.playingfieldmanagment.application.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoogleController {

    @Autowired
    private GoogleService googleService;

    @GetMapping("/searchByCity")
    public ResponseEntity<List<GoogleMapDto>> getPFsByCity(@RequestParam("city") String city) {
        return ResponseEntity.ok(googleService.getPlayingFieldsByCity(city));
    }

    @GetMapping("/searchByLocation")
    public ResponseEntity<List<GoogleMapDto>> getPFsByLocation(@RequestParam("lat") Double lat,
                                                               @RequestParam("lng") Double lng){
return ResponseEntity.ok(googleService.getPlayingFieldsByLocation(lat, lng));
    }
}
