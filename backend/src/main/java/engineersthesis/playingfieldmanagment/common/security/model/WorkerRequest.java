package engineersthesis.playingfieldmanagment.common.security.model;

import engineersthesis.playingfieldmanagment.application.model.PlayingField;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Data
@Table(name = "worker_request")
public class WorkerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "proof_of_work")
    @Lob
    private byte[] proofOfWork;


    @ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.ALL)
    @JoinColumn(name = "playing_field_id")
    private PlayingField playingField;

    public WorkerRequest() {
    }

    public WorkerRequest(Status status, String fileName, String fileType,
                         byte[] proofOfWork, PlayingField playingField) {
        this.status = status;
        this.fileName = fileName;
        this.fileType = fileType;
        this.proofOfWork = proofOfWork;

        this.playingField= playingField;
    }
    public WorkerRequest(Status status, String fileName, String fileType,
                         byte[] proofOfWork) {
        this.status = status;
        this.fileName = fileName;
        this.fileType = fileType;
        this.proofOfWork = proofOfWork;


    }
}
