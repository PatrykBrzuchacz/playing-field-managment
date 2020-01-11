package engineersthesis.playingfieldmanagment.modules.user.friends;


import engineersthesis.playingfieldmanagment.modules.security.model.User;

import javax.persistence.JoinColumn;
import java.io.Serializable;

public class FriendsKey implements Serializable {
    @JoinColumn(name = "user_one")
    private User userOne = null;
    @JoinColumn(name = "user_two")
    private User userTwo = null;
}
