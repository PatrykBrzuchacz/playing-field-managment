package engineersthesis.playingfieldmanagment.modules.playingField.setup;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "playing_field_setup")
public class PlayingFieldSetup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "team_size")
    private int teamSize;

    private String description;

    @Column(name = "playing_field_photo")
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] pfPhoto;
    @OneToOne
    @JoinColumn(name = "playing_field_id")
    private PlayingField playingField;

    private String name;

    public PlayingFieldSetup(int teamSize, String description, byte[] pfPhoto, PlayingField playingField, String name) {
        this.teamSize = teamSize;
        this.description = description;
        this.pfPhoto = pfPhoto;
        this.playingField = playingField;
        this.name = name;
    }
}
