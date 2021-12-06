package com.marriage_counsellors.marriagecounsellingsolution.controller;

import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/registration")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

   @PostMapping
    public String registerUser(HttpServletRequest request,
                               @ModelAttribute("user") UserDto userDto) {

        HttpSession httpSession = request.getSession();

        var user = userService.registerUser(userDto);

        if (user.getMessage().equals("User with the email :" + userDto.getEmail() + " already exist!!!")) {
            httpSession.setAttribute("message", "Failed to register or email already exist");
            return "redirect:/?" + "User with the email :" + userDto.getEmail() + " already exist!!!";
        }
        httpSession.setAttribute("message", "Successfully registered!!!");
        return "redirect:/?" + "User successfully registered!";
       // return "redirect:/successful?" + "User successfully registered!";
    }
}
