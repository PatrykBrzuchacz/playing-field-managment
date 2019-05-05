package engineersthesis.playingfieldmanagment.common.security.service;

import engineersthesis.playingfieldmanagment.common.security.exception.FileStorageException;
import engineersthesis.playingfieldmanagment.common.security.model.Request;
import engineersthesis.playingfieldmanagment.common.security.model.Status;
import engineersthesis.playingfieldmanagment.common.security.model.User;
import engineersthesis.playingfieldmanagment.common.security.model.UserCredentials;
import engineersthesis.playingfieldmanagment.common.security.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserService userService;

    public Request createWorker(UserCredentials user, MultipartFile file){
        User worker = userService.assignUserData(user);
        worker.setActive(false);

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

}
