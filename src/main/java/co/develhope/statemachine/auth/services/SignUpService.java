package co.develhope.statemachine.auth.services;

import co.develhope.statemachine.auth.dto.SignUpActivationDTO;
import co.develhope.statemachine.auth.dto.SignUpDTO;
import co.develhope.statemachine.notification.services.MailNotificationService;
import co.develhope.statemachine.user.dto.UserDTO;
import co.develhope.statemachine.user.entities.Role;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.repositories.RoleRepository;
import co.develhope.statemachine.user.services.UserService;
import co.develhope.statemachine.user.utils.Roles;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignUpService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO signUp(@NotNull SignUpDTO signUpDTO) {
        User userFromDB = userService.findByEmail(signUpDTO.getEmail());
        if(userFromDB != null) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setSurname(signUpDTO.getSurname());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setRecordStatus(false);
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(Roles.REGISTERED);
        if(userRole.isEmpty()) throw new RuntimeException("Cannot set role");
        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationMail(user);
        return userService.convertToDTO(userService.saveUser(user));
    }

    public UserDTO activate(@NotNull SignUpActivationDTO signUpActivationDTO) {
        User userFromDB = userService.findByActivationCode(signUpActivationDTO.getActivationCode());
        if(userFromDB == null) {
            throw new EntityNotFoundException("User not found");
        }
        userFromDB.setRecordStatus(true);
        userFromDB.setActivationCode(null);
        return userService.convertToDTO(userService.saveUser(userFromDB));
    }
}
