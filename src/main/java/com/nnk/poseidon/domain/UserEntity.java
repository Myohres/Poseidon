package com.nnk.poseidon.domain;


import com.nnk.poseidon.security.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    @ValidPassword
    private String password;
    private String fullname;
    private String role;

}
