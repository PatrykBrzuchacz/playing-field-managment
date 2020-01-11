package engineersthesis.playingfieldmanagment.modules.security.service;

import engineersthesis.playingfieldmanagment.modules.security.model.WorkerRequest;
import engineersthesis.playingfieldmanagment.web.dto.WorkerRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkerRequestAssembler {

    public List<WorkerRequestDto> toDtoList(List<WorkerRequest> workerRequests) {
        return workerRequests.stream().map(this::toDto).collect(Collectors.toList());
    }

    private WorkerRequestDto toDto(WorkerRequest workerRequest) {
        return new WorkerRequestDto(workerRequest.getId(),
                workerRequest.getUser().getUsername(),
                workerRequest.getStatus(),
                workerRequest.getFileName(), workerRequest.getPlayingField().getApiId(),
                workerRequest.getProofOfWork(), workerRequest.getPlayingField().getId());
    }
}
