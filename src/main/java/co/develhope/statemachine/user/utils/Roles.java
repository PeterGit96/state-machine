package co.develhope.statemachine.user.utils;

import co.develhope.statemachine.user.entities.User;

public class Roles {

    public final static String ADMIN = "ADMIN";
    public final static String REGISTERED = "REGISTERED";
    public final static String RESTAURANT = "RESTAURANT";
    public final static String RIDER = "RIDER";

    public static boolean hasRole(User user, String roleInput) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(roleInput));
    }

}
