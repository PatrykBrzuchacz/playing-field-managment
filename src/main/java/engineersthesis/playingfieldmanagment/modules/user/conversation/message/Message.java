package engineersthesis.playingfieldmanagment.modules.user.conversation.message;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.user.conversation.Conversation;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private User sender;

    @JoinColumn(name = "receiver_id")
    @ManyToOne
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    private String content;

    private LocalDateTime sended;

    public Message(User sender, User receiver, String content, LocalDateTime sended, Conversation conversation) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sended = sended;
        this.conversation = conversation;
    }

    @Override
    public int compareTo(Message o) {
        return this.getSended().compareTo(o.getSended());
    }

}

