package engineersthesis.playingfieldmanagment.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import engineersthesis.playingfieldmanagment.application.model.PlayingField;
import engineersthesis.playingfieldmanagment.application.model.dto.PlayingFieldSaveDto;
import engineersthesis.playingfieldmanagment.application.service.WorkerService;
import engineersthesis.playingfieldmanagment.common.security.model.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class PlayingFieldController {

    @Autowired
    private WorkerService workerService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/assignToWorker")
    public ResponseEntity<?> assignPlayingFieldToWorker(@RequestParam("playingField") String playingFieldDto,
                                                         @RequestParam("file") MultipartFile file) throws IOException {
        PlayingFieldSaveDto playingFieldSaveDto = new ObjectMapper().readValue(playingFieldDto, PlayingFieldSaveDto.class);
        workerService.assignPFToWorker(playingFieldSaveDto, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(value = "/assignToWorkerAndRegister")
    public ResponseEntity<?> assignPlayingFieldToWorker(@RequestParam("playingField") String playingFieldDto,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestParam("username") String username,
                                                        @RequestParam("password") String password) throws IOException {
        PlayingFieldSaveDto playingFieldSaveDto = new ObjectMapper().readValue(playingFieldDto, PlayingFieldSaveDto.class);
        workerService.assignPFAndRegisterWorker(playingFieldSaveDto, file, username, password);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
