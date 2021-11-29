package com.marriage_counsellors.marriagecounsellingsolution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto  implements Serializable {

    private String message;

    private String firstname;

    private String lastname;

    private String email;

    private String date0fBirth;

    private String gender;

    private String password;
}
