package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
public class TeamInviteDto {

    private Long id;
    private String teamName;
    private String inviteFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date dateAndTimeSended;
    private MatchDto matchDto;
    private Boolean isFull = false;

}
