package com.marriage_counsellors.marriagecounsellingsolution.configuration;

import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;

@Configuration
public class Test {


    private final UserServiceImpl userService;

    @Autowired
    public Test(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Transactional
    public void seed()  {
        UserDto userDto =  new UserDto("st","Kayode","stvolu@gmail.com",
                "sdsd","dsd");
        userService.saveUser(userDto);
    }
}
