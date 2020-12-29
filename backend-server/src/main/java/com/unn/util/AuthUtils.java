package com.unn.util;

import org.springframework.security.core.Authentication;

public abstract class AuthUtils {

    public static boolean notAuthenticated(Authentication auth) {
        return auth == null || !auth.isAuthenticated();
    }
}
