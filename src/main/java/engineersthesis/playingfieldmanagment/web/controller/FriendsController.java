package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.user.friends.FriendsService;
import engineersthesis.playingfieldmanagment.web.dto.FriendsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    @GetMapping("/{id}")
    public ResponseEntity<List<FriendsDto>> getAllFriends(@PathVariable("id") Long id) {
        return ResponseEntity.ok(friendsService.getAllFriends(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFriend(@PathVariable("id") Long id) {
        friendsService.deleteFriend(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/isFriend")
    public Boolean isFriend(@PathVariable("id") Long id) {
        return friendsService.isFriend(id);
    }
}
