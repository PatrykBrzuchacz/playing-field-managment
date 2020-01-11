package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.playingField.rating.RateService;
import engineersthesis.playingfieldmanagment.web.dto.RateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RateController {

    @Autowired
    private RateService rateService;

    @PostMapping("/playingField/{id}/addVote")
    private Double addVote(@PathVariable("id") Long id, @RequestParam("rate") Integer rate) {
        return rateService.addVote(id,rate);
    }
    @GetMapping("/playingField/{id}/getRate")
    private RateDto getRate(@PathVariable("id") Long id) {
        return rateService.getRate(id);
    }

    @GetMapping("/playingField/{id}/existsRate")
    private Boolean existsRateByLoggedUser(@PathVariable("id") Long id) {
        return rateService.existsRate(id);
    }
}
