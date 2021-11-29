package com.marriage_counsellors.marriagecounsellingsolution;

import com.marriage_counsellors.marriagecounsellingsolution.configuration.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarriageCounsellingSolutionApplication {
//        implements CommandLineRunner{
//
//    private final Test test;
//
//    @Autowired
//    public MarriageCounsellingSolutionApplication(Test test) {
//        this.test = test;
//    }

    public static void main(String[] args) {
        SpringApplication.run(MarriageCounsellingSolutionApplication.class, args);
    }


//    @Override
//    public void run(String... args) {
//        test.seed();
//    }
}
