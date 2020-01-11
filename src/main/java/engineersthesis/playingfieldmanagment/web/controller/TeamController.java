package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.team.TeamService;
import engineersthesis.playingfieldmanagment.web.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/match/{id}/teams")
    public ResponseEntity<List<TeamDto>> getTeamsByMatch(@PathVariable("id") Long id) {
        return ResponseEntity.ok(teamService.getTeamsByMatchId(id));
    }

    @PostMapping("/team/{id}/join")
    public void joinToTeam(@PathVariable("id") Long id) {
        teamService.joinToTeam(id);
    }

    @PutMapping("/match/{id}/swapTeam")
    public void swapTeam(@PathVariable("id") Long id) {
        teamService.swapTeams(id);
    }

    @DeleteMapping("/team/{id}/player/{userId}")
    public void deleteUserFromTeam(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        teamService.removePlayerFromTeam(id, userId);
    }

    @DeleteMapping("/team/{id}/exit")
    public void exitTeam(@PathVariable("id") Long id) {
        teamService.exitTeam(id);
    }
}
