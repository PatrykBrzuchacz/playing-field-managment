package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SearchDto {
    private String fromDate;
    private String toDate;
    private String fromTime;
    private String toTime;
    private boolean showPrivate = false;
    private boolean showFull = false;
    private int reserved = 1;
}
