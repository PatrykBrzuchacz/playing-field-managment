package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BanDto {
    private Long id;
    private Long playingFieldId;
    private Long userId;
    private String username;
}
