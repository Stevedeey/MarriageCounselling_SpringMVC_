package com.marriage_counsellors.marriagecounsellingsolution.services;

import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import com.marriage_counsellors.marriagecounsellingsolution.model.User;
import com.marriage_counsellors.marriagecounsellingsolution.repository.RoleRepository;
import com.marriage_counsellors.marriagecounsellingsolution.repository.UserRepository;
import com.marriage_counsellors.marriagecounsellingsolution.utility.RoleAssignment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleAssignment roleAssignment;
    private final RoleRepository roleRepository;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository userRepository, RoleAssignment roleAssignment, RoleRepository roleRepository) {
        super();
        this.userRepository = userRepository;
        this.roleAssignment = roleAssignment;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = null;


        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()){
          userDto.setMessage("User with the email :"+ userDto.getEmail() +" already exist!!!");
          return userDto;
        }else {

            List<String> stringList = new ArrayList<>();
            List<Role> roleList = roleAssignment.assignRole(stringList,roleRepository);
             user = User.builder()
                    .firstname(userDto.getFirstname())
                    .lastname(userDto.getLastname())
                    .email(userDto.getEmail())
                    .encryptedPassword(userDto.getPassword())
                    .gender("m")
                     .roles(roleList)
                    .dateOfBirth("userDto.getDate0fBirth()").build();
             saveUser(user);

        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        UserDto returnUserDto = modelMapper.map(user, UserDto.class);

        if(user != null) {
            returnUserDto.setMessage("Successfully Registered");
        }

        return returnUserDto;
    }


    public void saveUser(User user) {

        try {
            userRepository.save(user);

        } catch (Exception exception) {
            logger.error("Something went wrong! %f"+ exception.getMessage());
        }
    }
}
