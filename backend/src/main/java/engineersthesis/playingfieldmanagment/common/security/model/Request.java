package engineersthesis.playingfieldmanagment.common.security.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] proofOfWork;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Request(){}

    public Request(Status status, String fileName, String fileType, byte[] proofOfWork, User user){
        this.status=status;
        this.fileName=fileName;
        this.fileType=fileType;
        this.proofOfWork=proofOfWork;
        this.user=user;
    }
}
