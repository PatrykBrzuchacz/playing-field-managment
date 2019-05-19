package engineersthesis.playingfieldmanagment.common.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import engineersthesis.playingfieldmanagment.common.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.common.security.model.WorkerRequest;
import engineersthesis.playingfieldmanagment.common.security.service.WorkerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RepositoryRestController
public class RequestController {

    @Autowired
    private WorkerRequestService workerRequestService;

    @CrossOrigin
    @PostMapping(value = "/workerRequests/worker/signup")
    public ResponseEntity<?> saveWorker(@RequestParam("file") MultipartFile file,
                                        @RequestParam("userCredentials") String userCredentials) throws IOException {
        UserCredentials userCred = new ObjectMapper().readValue(userCredentials, UserCredentials.class);
        workerRequestService.createWorker(userCred, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/workerRequests")
    public ResponseEntity<List<WorkerRequest>> getRequestByStatusSended() {
        return ResponseEntity.ok(workerRequestService.findRequestBySendedStatus());
    }

    @PutMapping("/workerRequests/{id}")
    public ResponseEntity<?> manageRequest(@PathVariable("id") Long id, @RequestParam("decision") boolean decision) {
        workerRequestService.manageRequest(id, decision);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
