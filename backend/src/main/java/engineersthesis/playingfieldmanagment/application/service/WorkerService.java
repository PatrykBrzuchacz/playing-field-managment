package engineersthesis.playingfieldmanagment.application.service;

import engineersthesis.playingfieldmanagment.application.exception.PlayingFieldExistsInDatabaseException;
import engineersthesis.playingfieldmanagment.application.exception.UserIsWorkerException;
import engineersthesis.playingfieldmanagment.application.model.PlayingField;
import engineersthesis.playingfieldmanagment.application.model.dto.PlayingFieldSaveDto;
import engineersthesis.playingfieldmanagment.application.repository.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.common.security.exception.FileStorageException;
import engineersthesis.playingfieldmanagment.common.security.model.*;
import engineersthesis.playingfieldmanagment.common.security.repository.RequestRepository;
import engineersthesis.playingfieldmanagment.common.security.service.UserHelper;
import engineersthesis.playingfieldmanagment.common.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static engineersthesis.playingfieldmanagment.application.model.PlayingField.createPlayingFieldFromDto;

@Service
public class WorkerService {

    @Autowired
    private UserHelper userHelper;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void assignPFToWorker(PlayingFieldSaveDto playingFieldDto, MultipartFile file) {
        User user = userHelper.getLoggedUser();

        if (user.getRole().getName() == RoleName.ROLE_WORKER) {
            throw new UserIsWorkerException();
        }
        if (playingFieldRepository.findByApiIdAndIsRegisteredIsTrue(playingFieldDto.getApiId())!=null){
            throw new PlayingFieldExistsInDatabaseException();
        }

        PlayingField playingField = createPlayingFieldFromDto(playingFieldDto);
        playingField.setUser(userHelper.getLoggedUser());

        playingFieldRepository.save(playingField);

        saveRequest(file, user, playingField);
    }

    @Transactional
    public void assignPFAndRegisterWorker(PlayingFieldSaveDto playingFieldDto, MultipartFile file, String username, String password){
        User user = userService.createUser(new UserCredentials(username, password));
        user.setActive(false);

        if (playingFieldRepository.findByApiIdAndIsRegisteredIsTrue(playingFieldDto.getApiId())!=null){
            throw new PlayingFieldExistsInDatabaseException();
        }

        PlayingField playingField = createPlayingFieldFromDto(playingFieldDto);
        playingField.setUser(user);
        playingFieldRepository.save(playingField);
        saveRequest(file, user, playingField);
    }


    public void saveRequest(MultipartFile file, User user, PlayingField playingField) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

                WorkerRequest workerRequest = new WorkerRequest(Status.SENDED, fileName, file.getContentType(),
                        file.getBytes(), playingField);
            requestRepository.save(workerRequest);
        }
                catch (IOException ex) {
        throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
    }
    }
}