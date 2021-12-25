package com.marriage_counsellors.marriagecounsellingsolution.security;

import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import com.marriage_counsellors.marriagecounsellingsolution.model.User;
import com.marriage_counsellors.marriagecounsellingsolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: UserDetails"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), maRolesToAuthorities(user.getRoles()));
    }

    //method to map role to authority
    //all the roles the resent user has
    private List<? extends GrantedAuthority> maRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    }
}
