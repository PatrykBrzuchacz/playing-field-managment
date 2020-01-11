package engineersthesis.playingfieldmanagment.modules.security.model;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

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
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] proofOfWork;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playing_field_id")
    private PlayingField playingField;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public WorkerRequest() {
    }

    public WorkerRequest(Status status, String fileName, String fileType,
                         byte[] proofOfWork, PlayingField playingField, User user) {
        this.status = status;
        this.fileName = fileName;
        this.fileType = fileType;
        this.proofOfWork = proofOfWork;
        this.playingField = playingField;
        this.user = user;
    }

    public WorkerRequest(Status status, String fileName, String fileType,
                         byte[] proofOfWork) {
        this.status = status;
        this.fileName = fileName;
        this.fileType = fileType;
        this.proofOfWork = proofOfWork;


    }
}
