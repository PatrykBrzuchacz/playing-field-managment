package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InviteDto {
    private Long id;
    private MatchWithLocationDto matchWithLocationDto;
    private String senderUsername;
    private Long senderId;

}
