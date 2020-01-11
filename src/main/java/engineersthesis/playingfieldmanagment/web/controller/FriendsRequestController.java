package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest.FriendsRequestService;
import engineersthesis.playingfieldmanagment.web.dto.FriendsDto;
import engineersthesis.playingfieldmanagment.web.dto.FriendsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends/requests")
public class FriendsRequestController {

    @Autowired
    private FriendsRequestService friendsRequestService;

    @GetMapping
    public Page<FriendsRequestDto> getAllRequests(Pageable pageable) {
        return friendsRequestService.getAllUserRequests(pageable);
    }

    @GetMapping("/sendedRequests")
    public List<String> getSendedRequests() {
        return friendsRequestService.getSendedRequests();
    }

    @PostMapping("/users/{id}/send")
    public ResponseEntity<?> sendRequest(@PathVariable("id") Long id) {
        friendsRequestService.sendRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    public FriendsDto acceptRequest(@PathVariable("id") Long id) {
        return friendsRequestService.acceptRequest(id);
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<?> declineRequest(@PathVariable("id") Long id) {
        friendsRequestService.declineRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
