package com.marriage_counsellors.marriagecounsellingsolution.services;

import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserDto registerUser(UserDto userDto);
}
