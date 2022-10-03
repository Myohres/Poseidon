package com.nnk.poseidon.domain;


import com.nnk.poseidon.config.ValidPassword;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotEmpty
    @ValidPassword
    private String password;
    @NotBlank(message = "Fullname is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

}
