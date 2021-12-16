package com.marriage_counsellors.marriagecounsellingsolution.response;

import com.marriage_counsellors.marriagecounsellingsolution.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {
    private String message;
    private boolean status;
    private User user;
}
