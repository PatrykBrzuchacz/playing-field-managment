package engineersthesis.playingfieldmanagment.modules.user.conversation;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.user.conversation.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_one")
    private User userOne;

    @ManyToOne
    @JoinColumn(name = "user_two")
    private User userTwo;

    @Column(name = "last_message_date")
    private LocalDateTime lastMessage;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> messages;

    public Conversation(User userOne, User userTwo, LocalDateTime lastMessage) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.lastMessage = lastMessage;
    }

    void addMessage(Message message) {
        this.messages.add(message);
    }
}
