package com.marriage_counsellors.marriagecounsellingsolution.configuration;

import com.marriage_counsellors.marriagecounsellingsolution.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private  UserService userService;


    @Autowired
    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public PasswordEncoder   passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //To integrate spring data Jpa and Hibernate, we need to configure DaoAuthentication
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers(
                "/auth/register**",
                "/auth/new-user**",
                "auth**",
                "/images/**",
                "/script/**",
                "/styles/**",
                "/layout/**",
                "/",
                "/resources**",
                "/resources",
                "/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();

    }
}
