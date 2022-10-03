package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.UserEntity;
import com.nnk.poseidon.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = "ADMIN")
class UserControllerTest {


@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("userName");
        userEntity.setFullname("fullName@h");
        userEntity.setPassword("aaaaaaaaA8@");
        userEntity.setRole("role");
    }

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/user/list")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void addUser() throws Exception {
        mockMvc.perform(get("/user/add")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    void validateWithNoError() throws Exception {
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .param("username","uu")
                .param("password", "aaaaa@6A")
                .param("fullname", "ff")
                .param("role", "admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test

    void validateWithError() throws Exception {
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .param("username"," ")
                .param("password", "")
                .param("fullname", "")
                .param("role", ""))
                .andExpect(view().name("user/add"));
    }

    @Test

    void showUpdateForm() throws Exception {
        when(userService.findById(1)).thenReturn(userEntity);
        mockMvc.perform(get("/user/update/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userEntity"))
                .andExpect(view().name("user/update"));
    }

    @Test

    void showUpdateFormWithNoUserFound() throws Exception {
        doThrow(new NoSuchElementException()).when(userService).findById(any());
        mockMvc.perform(get("/user/update/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test

    void updateUserWithNoError() throws Exception {
        when((userService.save(userEntity))).thenReturn(userEntity);
        mockMvc.perform(post("/user/update/1")
                .with(csrf())
                .param("username","uu")
                .param("password", "aaaaaaaaA8@")
                .param("fullname", "ff")
                .param("role", "admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test

    void updateBidWithError() throws Exception {
        mockMvc.perform(post("/user/update/1")
                .with(csrf())
                .param("username"," ")
                .param("password", "")
                .param("fullname", "")
                .param("role", ""))
                .andExpect(view().name("user/update"));
    }

    @Test

    void deleteUser() throws Exception {
        when(userService.findById(any())).thenReturn(userEntity);
        when(userService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/delete/13")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test

    void getAllUser() throws Exception {
        when(userService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test

    void getUserById() throws Exception {
        when(userService.findById(any())).thenReturn(new UserEntity());
        mockMvc.perform(get("/user/userId/1")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test

    void getUserByIdNotFound() throws Exception {
        when(userService.findById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/user/userId/1")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test

    void adduser() throws Exception {
        mockMvc.perform(post("/user/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test

    void adduserNotFound() throws Exception {
        when(userService.save(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/user/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test

    void updateUser() throws Exception {
        when(userService.update(any())).thenReturn(userEntity);
        mockMvc.perform(put("/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test

    void updateUserNotFound() throws Exception {
        when(userService.update(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test

    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/userId/1")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test

    public void deleteuserByIdTestuserNotFound() throws Exception {
       doThrow(new NoSuchElementException()).when(userService).delete(1);
        mockMvc.perform(delete("/user/userId/1")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}