package co.develhope.statemachine.user.controllers;

import co.develhope.statemachine.auth.dto.LoginRTO;
import co.develhope.statemachine.auth.services.LoginService;
import co.develhope.statemachine.user.dto.UserDTO;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.services.UserService;
import co.develhope.statemachine.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('" + Roles.ADMIN + "' , '" + Roles.REGISTERED + "')")
    public LoginRTO getProfile(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        LoginRTO rto = new LoginRTO();
        rto.setUser(userService.convertToDTO(user));
        rto.setJWT(loginService.generateJWT(user));
        return rto;
    }

    @GetMapping
    @PreAuthorize("hasRole('" + Roles.ADMIN + "')")
    public List<UserDTO> getAllUsers(Principal principal) {
        return userService.getAllUsers(principal);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('" + Roles.ADMIN + "')")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('" + Roles.ADMIN + "')")
    public UserDTO updateUserById(@RequestBody UserDTO userEdit, @PathVariable Long id) {
        return userService.updateUserById(userEdit, id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('" + Roles.ADMIN + "')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User with id " + id + " deleted successfully");
    }

}
