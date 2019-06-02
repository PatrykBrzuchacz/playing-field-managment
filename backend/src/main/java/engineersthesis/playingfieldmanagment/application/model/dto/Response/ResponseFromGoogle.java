package engineersthesis.playingfieldmanagment.application.model.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ResponseFromGoogle {

    @JsonProperty("results")
    private List<Result> result = null;
    public ResponseFromGoogle(){}
}
