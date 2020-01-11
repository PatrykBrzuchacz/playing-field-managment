package engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "friends_request")
public class FriendsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private User sender;

    @JoinColumn(name = "receiver_id")
    @ManyToOne
    private User receiver;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "request_status")
    private FriendsRequestStatus requestStatus;

    public FriendsRequest(User sender, User receiver, FriendsRequestStatus requestStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestStatus = requestStatus;
    }
}
