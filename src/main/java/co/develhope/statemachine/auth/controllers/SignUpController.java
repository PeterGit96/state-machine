package co.develhope.statemachine.auth.controllers;

import co.develhope.statemachine.auth.dto.SignUpActivationDTO;
import co.develhope.statemachine.auth.dto.SignUpDTO;
import co.develhope.statemachine.auth.services.SignUpService;
import co.develhope.statemachine.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup")
    public UserDTO signup(@RequestBody SignUpDTO signUpDTO) {
        return signUpService.signUp(signUpDTO);
    }

    @PostMapping("/signup/activation")
    public UserDTO signup(@RequestBody SignUpActivationDTO signUpActivationDTO) {
        return signUpService.activate(signUpActivationDTO);
    }

}
