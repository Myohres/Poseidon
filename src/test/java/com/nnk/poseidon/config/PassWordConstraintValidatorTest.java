package com.nnk.poseidon.config;

import com.nnk.poseidon.domain.UserEntity;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class PassWordConstraintValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testInvalidPassword() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("userName");
        userEntity.setFullname("fullName");
        userEntity.setPassword("password");
        userEntity.setRole("role");

        Set<ConstraintViolation<UserEntity>> constraintViolations = validator.validate(userEntity);
        Assert.assertEquals(constraintViolations.size(),1);
    }

    @Test
    public void testValidPassword() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("userName");
        userEntity.setFullname("fullName");
        userEntity.setPassword("password8A@");
        userEntity.setRole("role");

        Set<ConstraintViolation<UserEntity>> constraintViolations = validator.validate(userEntity);
        Assert.assertEquals(constraintViolations.size(),0);
    }

}
