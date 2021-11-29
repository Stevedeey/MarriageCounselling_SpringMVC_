package com.marriage_counsellors.marriagecounsellingsolution.services;

import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.exception.ResourceNotFoundException;
import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import com.marriage_counsellors.marriagecounsellingsolution.model.User;
import com.marriage_counsellors.marriagecounsellingsolution.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = null;

        userDto.setMessage("User successfully registered! ");

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()){
          userDto.setMessage("User with the email :"+ userDto.getEmail() +" already exist!!!");
        }else {
             user = User.builder()
                    .firstname(userDto.getFirstname())
                    .lastname(userDto.getLastname())
                    .email(userDto.getEmail())
                    .encryptedPassword(userDto.getPassword())
                    .gender(userDto.getGender())
                    .dateOfBirth(userDto.getDate0fBirth()).build();
             saveUser(user);

        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto returnUserDto = modelMapper.map(user, UserDto.class);

        return returnUserDto;
    }


    public void saveUser(User user) {

        try {
            userRepository.save(user);

        } catch (Exception exception) {
            logger.error("Something went wrong! %f", exception.getMessage());
        }
    }
}
