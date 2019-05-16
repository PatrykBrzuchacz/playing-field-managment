package engineersthesis.playingfieldmanagment.common.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(value = EnumType.STRING)
    private RoleName name;

    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(long id, RoleName name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
