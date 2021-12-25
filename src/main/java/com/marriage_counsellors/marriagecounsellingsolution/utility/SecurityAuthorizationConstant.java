package com.marriage_counsellors.marriagecounsellingsolution.utility;

public class SecurityAuthorizationConstant {
    public static final String[] PUBLIC_URIS = new String[]{
            "/auth/**",
            "auth/login-user",
            "/images/**",
            "/script/**",
            "/styles/**",
            "/layout/**",
            "/assets/**", "/webjars/**","/static/**",
            "/"
           };

}
