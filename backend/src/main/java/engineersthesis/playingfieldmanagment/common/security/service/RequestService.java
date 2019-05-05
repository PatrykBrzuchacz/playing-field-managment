package engineersthesis.playingfieldmanagment.common.security.service;

import engineersthesis.playingfieldmanagment.common.security.exception.FileStorageException;
import engineersthesis.playingfieldmanagment.common.security.model.Request;
import engineersthesis.playingfieldmanagment.common.security.model.Status;
import engineersthesis.playingfieldmanagment.common.security.model.User;
import engineersthesis.playingfieldmanagment.common.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.common.security.repository.RequestRepository;
import engineersthesis.playingfieldmanagment.common.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    public Request createWorker(UserCredentials user, MultipartFile file){
        User worker = userService.assignUserData(user);
        worker.setActive(false);
        userRepository.save(worker);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Request request = new Request(Status.SENDED, fileName, file.getContentType(), file.getBytes(), worker);

            return requestRepository.save(request);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public User manageRequest(Long id, boolean decision) {
    Request request = requestRepository.getOne(id);
    if(decision){
        request.setStatus(Status.ACCEPTED);
        request.getUser().setActive(true);
    }
    else {
        request.setStatus(Status.DECLINED);
    }
    requestRepository.save(request);
    return userRepository.save(request.getUser());
    }

    public List<Request> findRequestBySendedStatus(){
        return requestRepository.findAllByStatus(Status.SENDED);
    }
}
