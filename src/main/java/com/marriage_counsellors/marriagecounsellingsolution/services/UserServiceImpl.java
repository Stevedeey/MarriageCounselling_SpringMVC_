package com.marriage_counsellors.marriagecounsellingsolution.services;

import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.exception.ErrorMessage;
import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import com.marriage_counsellors.marriagecounsellingsolution.model.User;
import com.marriage_counsellors.marriagecounsellingsolution.repository.RoleRepository;
import com.marriage_counsellors.marriagecounsellingsolution.repository.UserRepository;
import com.marriage_counsellors.marriagecounsellingsolution.utility.RoleAssignment;
import groovy.util.logging.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    private  final UserRepository userRepository;
    private final RoleAssignment roleAssignment;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserServiceImpl(UserRepository userRepository, RoleAssignment roleAssignment, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleAssignment = roleAssignment;
        this.roleRepository = roleRepository;
    }





    @Override
    public UserDto registerUser(UserDto userDto) {

        UserDto returnedUser = new UserDto();
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());


        try {
            if (optionalUser.isPresent()) {
                throw new ErrorMessage("The email " + userDto.getEmail() + "already exist!!");

            }

            List<String> stringList = new ArrayList<>();
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
            logger.info("UserDetails: "+ user);

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            returnedUser = modelMapper.map(user, UserDto.class);

            returnedUser.setMessage("Successfully Registered");
            returnedUser.setStatus(true);

            return returnedUser;

        } catch (Exception e) {

            returnedUser.setMessage("User already exist!! "+e.getMessage());
            returnedUser.setStatus(false);

            return returnedUser;

        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid Username or password"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), maRolesToAuthorities(user.getRoles()));
    }


    //method to map role to authority
    //all the roles the resent user has
    private List<? extends GrantedAuthority> maRolesToAuthorities(List<Role> roles){
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());   }

}
