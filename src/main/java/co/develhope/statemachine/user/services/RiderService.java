package co.develhope.statemachine.user.services;

import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private UserRepository userRepository;

    public User pickRider() {
        Optional<User> rider = userRepository.pickRider();
        //All riders are busy -> Take the first available
        return rider.orElseGet(() -> userRepository.findAll(PageRequest.of(0, 1)).toList().get(0));
    }

}
