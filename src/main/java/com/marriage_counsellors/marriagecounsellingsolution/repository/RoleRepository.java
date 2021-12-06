package com.marriage_counsellors.marriagecounsellingsolution.repository;

import com.marriage_counsellors.marriagecounsellingsolution.model.ERole;
import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
