package com.marriage_counsellors.marriagecounsellingsolution.controller;

import com.marriage_counsellors.marriagecounsellingsolution.dto.Questionnaire;
import com.marriage_counsellors.marriagecounsellingsolution.dto.UserDto;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("/questionnaire")
    public ModelAndView displayQuestionnaire(HttpServletRequest request) {

        Questionnaire questionnaire = new Questionnaire();
        ModelAndView modelAndView = new ModelAndView("questionnaire");
        modelAndView.addObject("questionnaireObj", questionnaire);

        return modelAndView;
    }
}
