package engineersthesis.playingfieldmanagment.modules.user.friends;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_one")
    private User userOne;
    @ManyToOne
    @JoinColumn(name = "user_two")
    private User userTwo;

    public Friends(User userOne, User userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
    }
}
