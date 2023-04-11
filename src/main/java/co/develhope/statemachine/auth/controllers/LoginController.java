package co.develhope.statemachine.auth.controllers;

import co.develhope.statemachine.auth.dto.LoginDTO;
import co.develhope.statemachine.auth.dto.LoginRTO;
import co.develhope.statemachine.auth.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        LoginRTO loginRTO = loginService.login(loginDTO);
        if(loginRTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials, cannot login");
        }
        return ResponseEntity.status(HttpStatus.OK).body(loginRTO);
    }

}
