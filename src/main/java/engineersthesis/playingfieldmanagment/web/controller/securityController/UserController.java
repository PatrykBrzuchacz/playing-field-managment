package engineersthesis.playingfieldmanagment.web.controller.securityController;


import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserService;
import engineersthesis.playingfieldmanagment.modules.security.service.UserService;
import engineersthesis.playingfieldmanagment.modules.team.Position;
import engineersthesis.playingfieldmanagment.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody RegisterUserDto registerUserDto) {
        securityUserService.createUserWithPosition(registerUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getLoggedUser")
    public UserDto getLoggedUserWithPlayingFields() {
        return securityUserService.getLoggedUser();
    }

    @GetMapping("/getLoggedUser/withAvatar")
    public UserDto getLoggedUserWithPlayingFieldsWithAvatar() {
        return securityUserService.getLoggedUserWithAvatar();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/username")
    public UserUsername getLoggedUserUsername(@PathVariable("id") Long id) {
        return securityUserService.getUserUsername(id);
    }

    @PutMapping("/{id}/editProfile")
    public void editUserProfile(@PathVariable("id") Long id, @RequestParam("age") String age,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                @RequestParam("position") Position position,
                                @RequestParam("city") String city,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("email") String email) {
        userService.editUserProfile(new EditUserDto(id, age, phoneNumber, avatar, position, city,
                firstName,
                lastName, email));
    }

    @PostMapping
    public List<UserFriendDto> getUsers(@RequestParam("city") String city, @RequestParam("position") Position position) {
        return userService.getUsers(city, position);
    }

    @PostMapping("/byUsername")
    public UserDto getUserByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/usernames/matches/{id}")
    public List<UserUsernameDto> getUsersUsername(@PathVariable("id") Long id, @RequestBody OnlyFriends onlyFriends) {
        return userService.getUsersUsername(id, onlyFriends.getOnlyFriends());
    }
}

