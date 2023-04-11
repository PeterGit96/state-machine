package co.develhope.statemachine.security;

import co.develhope.statemachine.auth.services.LoginService;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        if(user == null || !user.getRecordStatus()) {
            return List.of();
        }

        /*return Arrays.asList(user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toArray(SimpleGrantedAuthority[]::new));*/

        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(("ROLE_" + role.getName())))
                .collect(Collectors.toList());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get authorization header and validate

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate

        final String token;
        try {
            token = header.split("\\s")[1].trim();
        } catch(JWTVerificationException e) {
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decoded;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(LoginService.JWT_SECRET)).build();
            decoded = verifier.verify(token);
        } catch(JWTVerificationException exception) {
            filterChain.doFilter(request, response);
            return;
        }

        if(decoded == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context

        Optional<User> userDetails = userRepository.findById(decoded.getClaim("id").asLong());
        if (!userDetails.isPresent() || !userDetails.get().getRecordStatus()) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = userDetails.get();
        //user.setPassword(null);
        user.setActivationCode(null);
        user.setPasswordResetCode(null);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }
}