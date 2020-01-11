package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationDto {

    private String phoneNumber;
    private String email;
    private String lastName;
    private Boolean isPrivate;
}
