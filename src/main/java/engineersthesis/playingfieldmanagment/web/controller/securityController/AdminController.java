package engineersthesis.playingfieldmanagment.web.controller.securityController;

import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserService;
import engineersthesis.playingfieldmanagment.modules.security.service.UserService;
import engineersthesis.playingfieldmanagment.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AdminController {

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("userId") Long id) {
        securityUserService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/ban")
    public ResponseEntity<?> banAccount(@PathVariable("userId") Long id) {
        securityUserService.banUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/unban")
    public ResponseEntity<?> unbanAccount(@PathVariable("userId") Long id) {
        securityUserService.unbanUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/dismissWorker")
    public ResponseEntity<?> dismissWorker(@PathVariable("userId") Long id) {
        userService.dismissWorker(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/restoreAccount")
    public ResponseEntity<?> restoreAccount(@PathVariable("userId") Long id) {
        userService.restoreAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}