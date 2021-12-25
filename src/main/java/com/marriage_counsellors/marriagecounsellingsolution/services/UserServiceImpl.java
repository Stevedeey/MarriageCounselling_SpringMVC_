package com.marriage_counsellors.marriagecounsellingsolution.services;

import com.marriage_counsellors.marriagecounsellingsolution.dto.LoginDto;
import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.exception.ErrorMessage;
import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import com.marriage_counsellors.marriagecounsellingsolution.model.User;
import com.marriage_counsellors.marriagecounsellingsolution.repository.RoleRepository;
import com.marriage_counsellors.marriagecounsellingsolution.repository.UserRepository;
import com.marriage_counsellors.marriagecounsellingsolution.response.LoginResponse;
import com.marriage_counsellors.marriagecounsellingsolution.security.UserDetailServiceImpl;
import com.marriage_counsellors.marriagecounsellingsolution.utility.RoleAssignment;
import groovy.util.logging.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleAssignment roleAssignment;
    private final RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder passwordEncoder;
    private UserDetailServiceImpl userDetailService;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleAssignment roleAssignment,
                           RoleRepository roleRepository, AuthenticationManager authenticationManager,
                           UserDetailServiceImpl userDetailService) {
        this.userRepository = userRepository;
        this.roleAssignment = roleAssignment;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
    }


    @Override
    public UserDto registerUser(UserDto userDto) {

        UserDto returnedUser = new UserDto();
        Optional<User> optionalUser = findUserByUsername(userDto.getEmail());


        try {
            if (optionalUser.isPresent()) {
                throw new ErrorMessage("The email " + userDto.getEmail() + " already exist!!");

            }

            List<String> stringList = List.of("USER");
            List<Role> roleList = roleAssignment.assignRole(stringList, roleRepository);

            User user = User.builder()
                    .firstname(userDto.getFirstname())
                    .lastname(userDto.getLastname())
                    .email(userDto.getEmail())
                    .encryptedPassword(passwordEncoder.encode(userDto.getPassword()))
                    .gender("m")
                    .roles(roleList)
                    .dateOfBirth("userDto.getDate0fBirth()").build();

            userRepository.save(user);
            logger.info("UserDetails: " + user);

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            returnedUser = modelMapper.map(user, UserDto.class);

            returnedUser.setMessage("Successfully Registered");
            returnedUser.setStatus(true);

            return returnedUser;

        } catch (ErrorMessage e) {

            returnedUser.setMessage(e.getMessage());
            returnedUser.setStatus(false);

            return returnedUser;

        }

    }

    @Override
    public LoginResponse authenticate(LoginDto loginDto) {
        LoginResponse response = new LoginResponse();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginDto.getEmail(), loginDto.getPassword()
                            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
             UserDetails userDetails = userDetailService.loadUserByUsername(loginDto.getEmail());
           // var userDetails = (UserDetails) authentication.getPrincipal();

            Optional<User> userOptional = findUserByUsername(userDetails.getUsername());

            User user = userOptional.get();
            response.setStatus(true);
            response.setUser(user);
            response.setMessage("User logged in successfully");


        } catch (AuthenticationException e) {

            response.setStatus(false);
            response.setMessage("Login attempt failed  : Username or Password Incorrect");

        } catch (NullPointerException e) {
            response.setStatus(false);
            response.setMessage("No user with this email found!! ");

        }

        return response;
    }


    public Optional<User> findUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }


}
