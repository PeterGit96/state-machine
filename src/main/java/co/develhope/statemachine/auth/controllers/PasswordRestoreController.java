package co.develhope.statemachine.auth.controllers;

import co.develhope.statemachine.auth.dto.RequestPasswordDTO;
import co.develhope.statemachine.auth.dto.RestorePasswordDTO;
import co.develhope.statemachine.auth.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/password")
public class PasswordRestoreController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/request")
    public ResponseEntity<String> requestPassword(@RequestBody RequestPasswordDTO requestPasswordDTO) {
        try {
            passwordService.request(requestPasswordDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Reset password code sent");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials provided");
        }

    }

    @PostMapping("/restore")
    public ResponseEntity<String> restorePassword(@RequestBody RestorePasswordDTO restorePasswordDTO) {
        passwordService.restore(restorePasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Password restored successfully");
    }

}
