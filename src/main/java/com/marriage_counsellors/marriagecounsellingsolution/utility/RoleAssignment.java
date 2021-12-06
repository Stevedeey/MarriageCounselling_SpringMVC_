package com.marriage_counsellors.marriagecounsellingsolution.utility;


import com.marriage_counsellors.marriagecounsellingsolution.exception.ResourceNotFoundException;
import com.marriage_counsellors.marriagecounsellingsolution.model.ERole;
import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import com.marriage_counsellors.marriagecounsellingsolution.model.User;
import com.marriage_counsellors.marriagecounsellingsolution.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleAssignment {

    public List<Role> assignRole(List<String> userList, RoleRepository roleRepository) {

        List<Role> roles = new ArrayList<>();

        if (userList == null) {
            Role user = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new ResourceNotFoundException("No Such Role"));
            roles.add(user);
        } else {
            userList.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role admin = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role not found"));
                        roles.add(admin);

                        break;

                    default:
                        Role borrower = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role not found"));
                        roles.add(borrower);

                        break;
                }
            });
        }
        return roles;
    }
}
