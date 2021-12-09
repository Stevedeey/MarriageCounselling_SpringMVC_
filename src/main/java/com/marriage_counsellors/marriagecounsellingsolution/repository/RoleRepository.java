package com.marriage_counsellors.marriagecounsellingsolution.repository;

import com.marriage_counsellors.marriagecounsellingsolution.model.ERole;
import com.marriage_counsellors.marriagecounsellingsolution.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
