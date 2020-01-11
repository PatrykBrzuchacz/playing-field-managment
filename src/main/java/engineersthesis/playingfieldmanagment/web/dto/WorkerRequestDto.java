package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.security.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkerRequestDto {
    private Long id;
    private String username;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private String fileName;
    private String apiId;
    private byte[] proofOfWork;
    private Long pfId;
}
