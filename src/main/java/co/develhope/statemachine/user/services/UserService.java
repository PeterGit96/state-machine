package co.develhope.statemachine.user.services;

import co.develhope.statemachine.user.dto.UserDTO;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.repositories.UserRepository;
import co.develhope.statemachine.user.utils.Roles;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public User saveUser(@NotNull User user) {
        return userRepository.saveAndFlush(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode);
    }

    public User findByPasswordResetCode(String passwordResetCode) {
        return userRepository.findByPasswordResetCode(passwordResetCode);
    }

    public List<UserDTO> getAllUsers(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), Roles.ADMIN));
        if(!isAdmin) {
            return new ArrayList<>();
        }
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO updateUserById(@NotNull UserDTO userEdit, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if(userEdit.getName() != null) {
            user.setName(userEdit.getName());
        }
        if(userEdit.getSurname() != null) {
            user.setSurname(userEdit.getSurname());
        }
        if(userEdit.getEmail() != null) {
            user.setEmail(userEdit.getEmail());
        }

        return convertToDTO(userRepository.saveAndFlush(user));

    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public User convertToEntity(@NotNull UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToDTO(@NotNull User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
