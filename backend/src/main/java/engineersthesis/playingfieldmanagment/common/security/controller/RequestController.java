package engineersthesis.playingfieldmanagment.common.security.controller;

import engineersthesis.playingfieldmanagment.common.security.model.Request;
import engineersthesis.playingfieldmanagment.common.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.common.security.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RepositoryRestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/requests/worker/signup")
    public ResponseEntity<?> saveWorker(@RequestBody UserCredentials userCredentials,
                                      @RequestParam("file") MultipartFile file) {
        requestService.createWorker(userCredentials,file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Request>> getRequestByStatusSended(){
        return ResponseEntity.ok(requestService.findRequestBySendedStatus());
    }
    @PutMapping("/requests/{id}")
    public ResponseEntity<?> manageRequest(@PathVariable("id") Long id, @RequestParam("decision") boolean decision) {
        requestService.manageRequest(id, decision);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
