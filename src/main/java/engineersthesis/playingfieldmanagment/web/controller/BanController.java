package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.playingField.availability.ban.BanService;
import engineersthesis.playingfieldmanagment.web.dto.BanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BanController {

    @Autowired
    private BanService banService;

    @PostMapping("/playingField/{playingFieldId}/users/{userId}/ban")
    public BanDto banUser(@PathVariable("playingFieldId") Long playingFieldId, @PathVariable("userId") Long userId) {
        return banService.banUser(playingFieldId, userId);
    }

    @PutMapping("/playingField/{playingFieldId}/users/{userId}/unban")
    public void unbanUser(@PathVariable("playingFieldId") Long playingFieldId, @PathVariable("userId") Long userId) {
        banService.unbanUser(playingFieldId, userId);
    }

    @GetMapping("/playingField/{playingFieldId}/bans")
    public List<BanDto> getBansByPF(@PathVariable("playingFieldId") Long playingFieldId) {
        return banService.getBans(playingFieldId);
    }

    @GetMapping("/playingField/{playingFieldId}/users/{userId}/isBanned")
    public Boolean isBanned(@PathVariable("playingFieldId") Long playingFieldId,
                            @PathVariable("userId") Long userId) {
        return banService.isBanned(playingFieldId, userId);
    }
}
