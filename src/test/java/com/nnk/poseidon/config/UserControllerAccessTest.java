package com.nnk.poseidon.config;

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

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser()
public class UserControllerAccessTest {

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
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isForbidden());

    }

    @Test
    void addUser() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isForbidden());
    }

    @Test
    void validateWithNoError() throws Exception {
        mockMvc.perform(post("/user/validate")
                .param("username","uu")
                .param("password", "aaaaa@6A")
                .param("fullname", "ff")
                .param("role", "admin"))
                .andExpect(status().isForbidden());
    }

    @Test

    void validateWithError() throws Exception {
        mockMvc.perform(post("/user/validate")
                .param("username"," ")
                .param("password", "")
                .param("fullname", "")
                .param("role", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    void showUpdateForm() throws Exception {
        when(userService.findById(1)).thenReturn(userEntity);
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isForbidden());
    }

    @Test

    void showUpdateFormWithNoUserFound() throws Exception {
        doThrow(new NoSuchElementException()).when(userService).findById(any());
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isForbidden());
    }

    @Test

    void updateUserWithNoError() throws Exception {
        when((userService.save(userEntity))).thenReturn(userEntity);
        mockMvc.perform(post("/user/update/1")
                .param("username","uu")
                .param("password", "aaaaaaaaA8@")
                .param("fullname", "ff")
                .param("role", "admin"))
                .andExpect(status().isForbidden());
    }

    @Test

    void updateBidWithError() throws Exception {
        mockMvc.perform(post("/user/update/1")
                .param("username"," ")
                .param("password", "")
                .param("fullname", "")
                .param("role", ""))
                .andExpect(status().isForbidden());
    }

    @Test

    void deleteUser() throws Exception {
        when(userService.findById(any())).thenReturn(userEntity);
        when(userService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/delete/13"))
                .andExpect(status().isForbidden());
    }

    @Test

    void getAllUser() throws Exception {
        when(userService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/"))
                .andExpect(status().isForbidden());
    }

    @Test

    void getUserById() throws Exception {
        when(userService.findById(any())).thenReturn(new UserEntity());
        mockMvc.perform(get("/user/userId/1"))
                .andExpect(status().isForbidden());
    }

    @Test

    void getUserByIdNotFound() throws Exception {
        when(userService.findById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/user/userId/1"))
                .andExpect(status().isForbidden());
    }

    @Test

    void adduser() throws Exception {
        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test

    void adduserNotFound() throws Exception {
        when(userService.save(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test

    void updateUser() throws Exception {
        when(userService.update(any())).thenReturn(userEntity);
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test

    void updateUserNotFound() throws Exception {
        when(userService.update(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test

    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/userId/1"))
                .andExpect(status().isForbidden());
    }

    @Test

    public void deleteuserByIdTestuserNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(userService).delete(1);
        mockMvc.perform(delete("/user/userId/1"))
                .andExpect(status().isForbidden());
    }
}

