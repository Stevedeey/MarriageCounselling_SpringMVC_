package com.marriage_counsellors.marriagecounsellingsolution.services;

import com.marriage_counsellors.marriagecounsellingsolution.dto.LoginDto;
import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {
    public UserDto registerUser(UserDto userDto);
    public LoginResponse authenticate(LoginDto loginDto);
}
