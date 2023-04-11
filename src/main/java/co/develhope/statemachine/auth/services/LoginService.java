package co.develhope.statemachine.auth.services;

import co.develhope.statemachine.auth.dto.LoginDTO;
import co.develhope.statemachine.auth.dto.LoginRTO;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class LoginService {

    public static final String JWT_SECRET = "63a9afe6-8eff-4320-9bcc-d43d8dd1aca4";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginRTO login(@NotNull LoginDTO loginDTO) {
        User userFromDB = userService.findByEmail(loginDTO.getEmail());
        if(userFromDB == null || !userFromDB.getRecordStatus()) {
            return null;
        }
        boolean canLogin = this.canUserLogin(userFromDB, loginDTO.getPassword());
        if(!canLogin) {
            return null;
        }

        String JWT = getJWT(userFromDB);
        userFromDB.setJwtCreatedOn(LocalDateTime.now());
        userService.saveUser(userFromDB);

        userFromDB.setPassword(null);
        return new LoginRTO(userService.convertToDTO(userFromDB), JWT);
    }

    public boolean canUserLogin(@NotNull User user, @NotNull String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    private static Date convertToDateViaInstant(@NotNull LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String getJWT(@NotNull User user) {
        Date expiresAt = convertToDateViaInstant(LocalDateTime.now().plusDays(15));
        String[] roles = user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new);
        return JWT.create()
                .withIssuer("login-system")
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt)
                .withClaim("roles", String.join(", ", roles))
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public String generateJWT(User user) {
        String JWT = getJWT(user);

        user.setJwtCreatedOn(LocalDateTime.now());
        userService.saveUser(user);

        return JWT;
    }

}
