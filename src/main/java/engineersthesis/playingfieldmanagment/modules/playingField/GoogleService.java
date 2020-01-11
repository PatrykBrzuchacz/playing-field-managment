package engineersthesis.playingfieldmanagment.modules.playingField;

import engineersthesis.playingfieldmanagment.modules.infrastructure.GoogleApiComponent;
import engineersthesis.playingfieldmanagment.modules.security.service.UserAssembler;
import engineersthesis.playingfieldmanagment.web.dto.GoogleMapDto;
import engineersthesis.playingfieldmanagment.web.dto.GooglePlaceLocationDto;
import engineersthesis.playingfieldmanagment.web.dto.Response.ResponseFromGoogle;
import engineersthesis.playingfieldmanagment.web.dto.Response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoogleService {

    @Autowired
    private GoogleApiComponent googleApiComponent;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private UserAssembler userAssembler;

    public List<GoogleMapDto> getPlayingFieldsByCity(String city) {
        String url = googleApiComponent.getSearchedUrlByCity();
        Map<String, Object> uriVariables = googleApiComponent.getUriVariablesByCity(city);

        ResponseFromGoogle responseFromGoogle = getResponseFromGoogle(url, uriVariables);

        return getPlayingFieldsFromResponse(responseFromGoogle);
    }

    public List<GoogleMapDto> getPlayingFieldsByLocation(Double lat, Double lng) {
        String url = googleApiComponent.getSearchedUrlByLocation();
        Map<String, Object> uriVariables = googleApiComponent.getUriVariablesByLocation(lat, lng);

        ResponseFromGoogle responseFromGoogle = getResponseFromGoogle(url, uriVariables);
        return getPlayingFieldsFromResponse(responseFromGoogle);
    }

    public List<GoogleMapDto> getPlayingFieldsFromResponse(ResponseFromGoogle responseFromGoogle) {
        return responseFromGoogle.getResult().stream().map(this::getPlayingFieldFromResult).collect(Collectors.toList());
    }

    public GoogleMapDto getPlayingFieldFromResult(Result result) {
        GooglePlaceLocationDto googlePlaceLocationDto =
                new GooglePlaceLocationDto(
                        result.getGeometry().location.lat,
                        result.getGeometry().location.lng,
                        result.getFormatted_address()
                );

        PlayingField playingField =
                playingFieldRepository.findByAddressAndIsRegisteredIsTrue(result.getFormatted_address());
        if (playingField != null) {
            return new GoogleMapDto(playingField.getId(), result.getApiId(), playingField.getName(),
                    googlePlaceLocationDto, true, userAssembler.toDto(playingField.getUser()),
                    playingField.getPlayingFieldSetup() != null,playingField.getRating());
        } else {
            return new GoogleMapDto(result.getApiId(), result.getName(),
                    googlePlaceLocationDto, false);
        }
    }


    public ResponseFromGoogle getResponseFromGoogle(String url, Map<String, Object> uriVariables) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ResponseFromGoogle.class, uriVariables);
    }
}
