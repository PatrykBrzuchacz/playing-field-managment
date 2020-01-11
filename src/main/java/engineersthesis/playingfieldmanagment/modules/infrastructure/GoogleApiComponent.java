package engineersthesis.playingfieldmanagment.modules.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GoogleApiComponent {

    public static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    @Value("${api.google.key}")
    private String googleKey;

    @Value("${api.google.playing.field}")
    private String pfobject;

    public String getSearchedUrlByCity() {
        return GOOGLE_API_URL +
                "key={googleKey}" +
                "&query={city},{pfObject}";
    }

    public String getSearchedUrlByLocation() {
        return GOOGLE_API_URL +
                "key={googleKey}" +
                "&query={pfObject}" +
                "&location={lat},{lng}";
    }

    public Map<String, Object> getUriVariablesByCity(String city) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("googleKey", googleKey);
        uriVariables.put("city", city);
        uriVariables.put("pfObject", pfobject);
        return uriVariables;
    }

    public Map<String, Object> getUriVariablesByLocation(Double lat, Double lng) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("googleKey", googleKey);
        uriVariables.put("pfObject", pfobject);
        uriVariables.put("lat", lat);
        uriVariables.put("lng", lng);
        return uriVariables;
    }
}
