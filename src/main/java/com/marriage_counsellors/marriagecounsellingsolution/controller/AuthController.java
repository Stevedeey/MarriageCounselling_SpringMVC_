package com.marriage_counsellors.marriagecounsellingsolution.controller;

import com.marriage_counsellors.marriagecounsellingsolution.dto.LoginDto;
import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.response.LoginResponse;
import com.marriage_counsellors.marriagecounsellingsolution.services.UserService;
import com.marriage_counsellors.marriagecounsellingsolution.services.UserServiceImpl;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(HttpServletRequest request) {

        UserDto userDto = new UserDto();
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("newUser", userDto);

        return modelAndView;
    }

    @PostMapping("/new-user")
    public String registerUser(HttpServletRequest request,
                               @ModelAttribute("newUser") UserDto userDto,
                               Model model, RedirectAttributes redirectAttributes) {
        UserDto user = userService.registerUser(userDto);

        if (user.isStatus()) {
            redirectAttributes.addFlashAttribute("message", user.getMessage());

            return "redirect:/";
        }

        model.addAttribute("errorMessage", user.getMessage());
        return "registration";
    }

    @GetMapping("/")
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.removeAttribute("message");

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("newUser", new UserDto());

        return mav;
    }

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new LoginDto());
        return "login";
    }

    @GetMapping("/show-login")
    public String handleLogin(){
        return "/login-user";
    }

    @PostMapping("/login-user")
    public ModelAndView doLogin(@ModelAttribute("loginUserObj") LoginDto loginDto,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        LoginResponse login = userService.authenticate(loginDto);

        if (login.isStatus()) {

            modelAndView.addObject("user", login.getUser());
            modelAndView.addObject("message", login.getMessage());
            modelAndView.setViewName("home");
            return modelAndView;

        }

        modelAndView.addObject("errMessage", login.getMessage());
        modelAndView.setViewName("login");

        return modelAndView;
    }


}
