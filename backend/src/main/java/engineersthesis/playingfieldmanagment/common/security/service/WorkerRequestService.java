package engineersthesis.playingfieldmanagment.common.security.service;

import engineersthesis.playingfieldmanagment.application.model.PlayingField;
import engineersthesis.playingfieldmanagment.application.repository.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.common.security.exception.FileStorageException;
import engineersthesis.playingfieldmanagment.common.security.model.*;
import engineersthesis.playingfieldmanagment.common.security.repository.RequestRepository;
import engineersthesis.playingfieldmanagment.common.security.repository.RoleRepository;
import engineersthesis.playingfieldmanagment.common.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class WorkerRequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;

    public WorkerRequest createWorker(UserCredentials userCredentials, MultipartFile file) {
        User worker = userService.assignUserData(userCredentials);
        worker.setRole(roleRepository.findByName(RoleName.ROLE_WORKER));
        userRepository.save(worker);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            WorkerRequest workerRequest = new WorkerRequest(Status.SENDED, fileName, file.getContentType(),
                    file.getBytes());

            return requestRepository.save(workerRequest);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public void manageRequest(Long id, boolean decision) {
        WorkerRequest workerRequest = requestRepository.getOne(id);
        if (decision) {
            for(WorkerRequest request: requestRepository.findAll()) {
                if (request.getPlayingField().getApiId()
                        .equals(workerRequest.getPlayingField().getApiId()) &&
                        !request.equals(workerRequest)) {
                    request.setStatus(Status.DECLINED);
                    requestRepository.save(request);
                }
            }

            workerRequest.getPlayingField().getUser().setActive(true);
            workerRequest.setStatus(Status.ACCEPTED);
            workerRequest.getPlayingField().getUser().setRole(roleRepository.findByName(RoleName.ROLE_WORKER));
            workerRequest.getPlayingField().setRegistered(true);
        } else {
            workerRequest.setStatus(Status.DECLINED);
        }

        requestRepository.save(workerRequest);

    }

    public List<WorkerRequest> findRequestBySendedStatus() {
        return requestRepository.findAllByStatus(Status.SENDED);
    }
}
