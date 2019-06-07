package engineersthesis.playingfieldmanagment.application.service;

import engineersthesis.playingfieldmanagment.application.model.PlayingField;
import engineersthesis.playingfieldmanagment.application.model.dto.GoogleMapDto;
import engineersthesis.playingfieldmanagment.application.model.dto.GooglePlaceLocationDto;
import engineersthesis.playingfieldmanagment.application.model.dto.Response.ResponseFromGoogle;
import engineersthesis.playingfieldmanagment.application.model.dto.Response.Result;
import engineersthesis.playingfieldmanagment.application.service.helper.GoogleApiComponent;
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

    public List<GoogleMapDto> getPlayingFieldsByCity(String city) {
        String url = googleApiComponent.getSearchedUrlByCity();
        Map<String, Object> uriVariables = googleApiComponent.getUriVariablesByCity(city);

        ResponseFromGoogle responseFromGoogle = getResponseFromGoogle(url, uriVariables);

        return getPlayingFieldsFromResponse(responseFromGoogle);
    }

    public List<GoogleMapDto> getPlayingFieldsByLocation(Double lat, Double lng){
        String url = googleApiComponent.getSearchedUrlByLocation();
        Map<String, Object> uriVariables = googleApiComponent.getUriVariablesByLocation(lat,lng);

        ResponseFromGoogle responseFromGoogle = getResponseFromGoogle(url, uriVariables);

        return getPlayingFieldsFromResponse(responseFromGoogle);
    }
    public List<GoogleMapDto> getPlayingFieldsFromResponse(ResponseFromGoogle responseFromGoogle){
        return responseFromGoogle.getResult().stream().map(this::getPlayingFieldFromResult).collect(Collectors.toList());
    }

    public GoogleMapDto getPlayingFieldFromResult(Result result){
        GooglePlaceLocationDto googlePlaceLocationDto =
                new GooglePlaceLocationDto(
                        result.getGeometry().location.lat,
                        result.getGeometry().location.lng,
                        result.getFormatted_address()
                        );

        return new GoogleMapDto(result.getApiId(),result.getName(),
                googlePlaceLocationDto);
    }



    public ResponseFromGoogle getResponseFromGoogle(String url,Map<String, Object> uriVariables){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ResponseFromGoogle.class, uriVariables);
    }
}
