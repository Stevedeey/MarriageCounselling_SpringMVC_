package com.marriage_counsellors.marriagecounsellingsolution.configuration;

import com.marriage_counsellors.marriagecounsellingsolution.security.UserDetailServiceImpl;
import com.marriage_counsellors.marriagecounsellingsolution.services.UserService;
import com.marriage_counsellors.marriagecounsellingsolution.utility.SecurityAuthorizationConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Repository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailService;


    @Bean
    public PasswordEncoder   passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //To integrate spring data Jpa and Hibernate, we need to configure DaoAuthentication
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(this.userDetailService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .antMatcher("/auth/**")
                .authorizeRequests()
                .antMatchers(
                 SecurityAuthorizationConstant.PUBLIC_URIS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/show-login")
                .defaultSuccessUrl("/auth/login-user",true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/auth/login?logout")
                .permitAll();

        /***

         .formLogin()
         .loginPage("/login.html")
         .loginProcessingUrl("/perform_login")
         .defaultSuccessUrl("/homepage.html", true)
         .failureUrl("/login.html?error=true")
         .failureHandler(authenticationFailureHandler())
         */

    }
}
