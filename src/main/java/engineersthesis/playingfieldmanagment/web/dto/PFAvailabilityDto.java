package engineersthesis.playingfieldmanagment.web.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PFAvailabilityDto {
    private LocalDate openDatePF;
    private LocalDate closeDatePF;
    private LocalTime openTimePF;
    private LocalTime closeTimePF;
    private int matchTime;
}
