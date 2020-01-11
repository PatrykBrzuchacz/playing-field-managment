package engineersthesis.playingfieldmanagment.modules.security.service;

import engineersthesis.playingfieldmanagment.modules.security.model.*;
import engineersthesis.playingfieldmanagment.modules.security.repository.RequestRepository;
import engineersthesis.playingfieldmanagment.modules.security.repository.RoleRepository;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.web.dto.WorkerRequestDto;
import engineersthesis.playingfieldmanagment.web.exception.securityException.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class WorkerRequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private WorkerRequestAssembler workerRequestAssembler;

    public WorkerRequest createWorker(UserCredentials userCredentials, MultipartFile file) {
        User worker = securityUserService.assignUserData(userCredentials);
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
            for (WorkerRequest request : requestRepository.findAll()) {
                if (request.getPlayingField().getApiId()
                        .equals(workerRequest.getPlayingField().getApiId()) &&
                        !request.equals(workerRequest)) {
                    request.setStatus(Status.DECLINED);
                    requestRepository.save(request);
                }
            }

            workerRequest.getUser().setActive(true);
            workerRequest.setStatus(Status.ACCEPTED);
            workerRequest.getUser().setRole(roleRepository.findByName(RoleName.ROLE_WORKER));
            workerRequest.getPlayingField().setRegistered(true);
            workerRequest.getPlayingField().setUser(workerRequest.getUser());
        } else {
            workerRequest.setStatus(Status.DECLINED);
        }
        requestRepository.save(workerRequest);
    }

    public List<WorkerRequestDto> findRequestBySendedStatus() {
        return workerRequestAssembler.toDtoList(requestRepository.findAllByStatus(Status.SENDED));
    }
}
