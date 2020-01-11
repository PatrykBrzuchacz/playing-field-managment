package engineersthesis.playingfieldmanagment.modules.worker;


import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.*;
import engineersthesis.playingfieldmanagment.modules.security.repository.RequestRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserService;
import engineersthesis.playingfieldmanagment.web.dto.PlayingFieldSaveDto;
import engineersthesis.playingfieldmanagment.web.exception.PlayingFieldExistsInDatabaseException;
import engineersthesis.playingfieldmanagment.web.exception.UserIsWorkerException;
import engineersthesis.playingfieldmanagment.web.exception.securityException.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static engineersthesis.playingfieldmanagment.modules.playingField.PlayingField.createPlayingFieldFromDto;


@Service
public class WorkerService {

    @Autowired
    private SecurityUserHelper userHelper;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private SecurityUserService securityUserService;

    @Transactional
    public void assignPFToWorker(PlayingFieldSaveDto playingFieldDto, MultipartFile file) {
        User user = userHelper.getLoggedUser();

        if (user.getRole().getName() == RoleName.ROLE_WORKER) {
            throw new UserIsWorkerException();
        }
        if (playingFieldRepository.findByAddressAndIsRegisteredIsTrue(playingFieldDto.getFormattedAddress()) != null) {
            throw new PlayingFieldExistsInDatabaseException();
        }

        PlayingField playingField = createPlayingFieldFromDto(playingFieldDto);

        playingFieldRepository.save(playingField);

        saveRequest(file, user, playingField);
    }

    @Transactional
    public void assignPFAndRegisterWorker(PlayingFieldSaveDto playingFieldDto, MultipartFile file, String username, String password) {
        User user = securityUserService.createUser(new UserCredentials(username, password));
        user.setActive(false);

        if (playingFieldRepository.findByAddressAndIsRegisteredIsTrue(playingFieldDto.getFormattedAddress()) != null) {
            throw new PlayingFieldExistsInDatabaseException();
        }

        PlayingField playingField = createPlayingFieldFromDto(playingFieldDto);
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
                    file.getBytes(), playingField, user);
            requestRepository.save(workerRequest);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


}