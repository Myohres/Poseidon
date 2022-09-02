package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.UserEntity;
import com.nnk.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserEntity user;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1);
        user.setUsername("userName");
        user.setFullname("fullName");
        user.setPassword("password");
        user.setRole("role");
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(new ArrayList<UserEntity>());
        userService.findAll();
        verify(userRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        UserEntity userResult = userService.findById(1);
        assertEquals(1,userResult.getId());
    }

    @Test
    void findByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.findById(1));
    }

    @Test
    void save() {
        when(userRepository.save(any())).thenReturn(user);
        userService.save(user);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    void update() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("userName2");
        userEntity.setFullname("fullName2");
        userEntity.setPassword("password2");
        userEntity.setRole("role2");

        userService.update(userEntity);

        assertEquals(1, user.getId());
        assertEquals("userName", user.getUsername());
        assertEquals("fullName", user.getFullname());
        assertEquals("password2", user.getPassword());
        assertEquals("role", user.getRole());
    }

    @Test
    void delete() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.delete(1);
        verify(userRepository,times(1)).delete(user);
    }
}