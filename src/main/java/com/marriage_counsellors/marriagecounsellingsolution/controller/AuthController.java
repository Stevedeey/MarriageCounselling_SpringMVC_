package com.marriage_counsellors.marriagecounsellingsolution.controller;

import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import com.marriage_counsellors.marriagecounsellingsolution.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(HttpServletRequest request){
//        HttpSession session =  request.getSession();
//        session.removeAttribute("message");

        UserDto userDto = new UserDto();
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("newUser", userDto);

        return modelAndView;
    }

   @PostMapping("/new-user")
    public String registerUser(HttpServletRequest request,
                               @ModelAttribute("newUser") UserDto userDto,
                               Model model, RedirectAttributes redirectAttributes) {
        HttpSession httpSession = request.getSession();
        UserDto user = userService.registerUser(userDto);

        if(user.isStatus()){
            redirectAttributes.addFlashAttribute("message", user.getMessage());
           // httpSession.setAttribute("message", "Successfully registered!!!");

            return "redirect:/";
        }


      //  httpSession.setAttribute("message", "Failed to register or email already exist");
        model.addAttribute("message",user.getMessage());
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
    public String login(){
        return "login";
    }

}
