package com.marriage_counsellors.marriagecounsellingsolution.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 36, updatable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private  String lastname;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;



}
