package engineersthesis.playingfieldmanagment.web.controller.securityController;

import com.fasterxml.jackson.databind.ObjectMapper;
import engineersthesis.playingfieldmanagment.modules.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.modules.security.service.WorkerRequestService;
import engineersthesis.playingfieldmanagment.web.dto.WorkerRequestDto;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/workerRequests")
    public ResponseEntity<List<WorkerRequestDto>> getRequestByStatusSended() {
        return ResponseEntity.ok(workerRequestService.findRequestBySendedStatus());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/workerRequests/{id}")
    public ResponseEntity<?> manageRequest(@PathVariable("id") Long id, @RequestParam("decision") boolean decision) {
        workerRequestService.manageRequest(id, decision);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
