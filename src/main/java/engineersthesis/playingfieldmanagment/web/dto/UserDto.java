package engineersthesis.playingfieldmanagment.web.dto;

import engineersthesis.playingfieldmanagment.modules.team.Position;
import lombok.Data;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Boolean banned;
    private Boolean active;
    private LocalDateTime registered;
    private LocalDateTime lastLogin;
    private String email;
    private String phoneNumber;
    private String lastName;
    private Long playingFieldId;
    private Integer age;
    private String firstName;
    private Position position;
    private String city;
    @Lob
    private byte[] avatar;
    private Integer matchesCount;
    private Integer warnings;

    public UserDto(Long id, String username, Boolean banned, Boolean active, LocalDateTime registered,
                   LocalDateTime lastLogin, String email, String phoneNumber, String lastName, Integer age,
                   String firstName, Position position, String city, byte[] avatar, Integer matchesCount,
                   Integer warnings, Long playingFieldId) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.active = active;
        this.registered = registered;
        this.lastLogin = lastLogin;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastName = lastName;
        this.age = age;
        this.firstName = firstName;
        this.position = position;
        this.city = city;
        this.avatar = avatar;
        this.matchesCount=matchesCount;
        this.warnings = warnings;
        this.playingFieldId=playingFieldId;
    }

    public void setPlayingFieldId(Long id) {
        this.playingFieldId = id;
    }
}
