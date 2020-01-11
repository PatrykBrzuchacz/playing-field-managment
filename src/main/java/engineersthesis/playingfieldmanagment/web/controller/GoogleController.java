package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.playingField.GoogleService;
import engineersthesis.playingfieldmanagment.web.dto.GoogleMapDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                                               @RequestParam("lng") Double lng) {
        return ResponseEntity.ok(googleService.getPlayingFieldsByLocation(lat, lng));
    }
}
