package co.develhope.statemachine.auth.services;

import co.develhope.statemachine.auth.dto.RequestPasswordDTO;
import co.develhope.statemachine.auth.dto.RestorePasswordDTO;
import co.develhope.statemachine.notification.services.MailNotificationService;
import co.develhope.statemachine.user.dto.UserDTO;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private UserService userService;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO request(@NotNull RequestPasswordDTO requestPasswordDTO) {
        User userFromDB = userService.findByEmail(requestPasswordDTO.getEmail());
        if(userFromDB == null) {
            throw new EntityNotFoundException("User not found");
        }
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());
        mailNotificationService.sendPasswordResetMail(userFromDB);
        return userService.convertToDTO(userService.saveUser(userFromDB));
    }

    public UserDTO restore(@NotNull RestorePasswordDTO restorePasswordDTO) {
        User userFromDB = userService.findByPasswordResetCode(restorePasswordDTO.getPasswordResetCode());
        if (userFromDB == null) {
            throw new EntityNotFoundException("User not found");
        }
        userFromDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);
        userFromDB.setRecordStatus(true);
        userFromDB.setActivationCode(null);
        return userService.convertToDTO(userService.saveUser(userFromDB));
    }

}
