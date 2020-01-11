package engineersthesis.playingfieldmanagment.web.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseFromGoogle {

    @JsonProperty("results")
    private List<Result> result = null;

    public ResponseFromGoogle() {
    }
}
