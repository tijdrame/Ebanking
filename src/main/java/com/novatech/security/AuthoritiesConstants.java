package com.novatech.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";//= Abonne

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String GESTIONNAIRE = "ROLE_GESTIONNAIRE";

    private AuthoritiesConstants() {
    }
}
