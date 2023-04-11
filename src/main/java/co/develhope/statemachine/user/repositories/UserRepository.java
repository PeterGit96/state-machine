package co.develhope.statemachine.user.repositories;

import co.develhope.statemachine.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByActivationCode(String activationCode);
    User findByPasswordResetCode(String passwordResetCode);

    @Query(nativeQuery = true, value = """
            SELECT * FROM (
                SELECT u.*, COUNT(busyOrders.id) AS numberOfOrders
                FROM `user` AS u
                LEFT JOIN user_roles AS ur ON ur.user_id = u.id\s
                LEFT JOIN ( SELECT * FROM `orders` WHERE `order_state` IN(4) ) AS busyOrders ON busyOrders.rider_id = u.id
                WHERE ur.role_id = 2  AND u.record_status = 1
                GROUP BY u.id
            ) AS allRiders\s
            WHERE allRiders.numberOfOrders = 0\s
            LIMIT 1""")
    Optional<User> pickRider();

}