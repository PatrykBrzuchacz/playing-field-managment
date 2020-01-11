package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto implements Comparable<MatchDto> {
    private Long id;
    private LocalDateTime matchFrom;
    private LocalDateTime matchTo;
    private Boolean isBooked;
    private int size;
    private int maxSize;
    private Boolean isPrivate;
    private Long ownerId;
    private String ownerUsername;
    private Boolean editable;
    private String code;
    private Boolean isCodeFilled;
    private String phoneNumber;
    private String surname;
    private String email;

    @Override
    public int compareTo(MatchDto o) {
        return o.getMatchFrom().compareTo(matchFrom);
    }
}
