package engineersthesis.playingfieldmanagment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchWithLocationDto {
    private Long id;
    private LocalDateTime matchFrom;
    private LocalDateTime matchTo;
    private String address;
    private String name;
    private double lat;
    private double lng;
    private int size;
    private int maxSize;
    private Boolean isBooked;
    private Boolean isPrivate;
    private Long ownerId;
    private String ownerUsername;
    private boolean isBanned;
    private boolean editable;
    private String code;
    private Double rate;
    private Long pfId;
}
