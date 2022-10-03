package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.UserEntity;
import com.nnk.poseidon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@Controller
@CrossOrigin("http://localhost:4200")
public class UserController {
    /**
     * UserService layer.
     */
    @Autowired
    private UserService userService;

    /**
     * Get user home page.
     * @param model user list model
     * @return user list home page.
     */
    @RequestMapping("/user/list")
    public String home(final Model model) {
        log.info("GET/user/list");
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Get adding user page.
     * @param userEntity user form to add
     * @return user add page
     */
    @GetMapping("/user/add")
    public String addUser(final UserEntity userEntity) {
        log.info("GET/user/add");
        return "user/add";
    }

    /**
     * Post Adding user page.
     * @param user user to add
     * @param result binding
     * @param model user to pass
     * @return user home page or user add page
     */
    @PostMapping("/user/validate")
    public String validate(@Valid final UserEntity user,
                          final BindingResult result, final Model model) {
        log.info("POST/user/validate");
        if (!result.hasErrors()) {
           BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
            log.info("validate succes");
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        log.info("validate error");
        return "user/add";
    }

    /**
     * Get updating user page.
     * @param id user id
     * @param model user to pass
     * @return updating user page validate or user list page
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        log.info("GET/user/update/" + id);
        try {
            UserEntity user = userService.findById(id);
            user.setPassword("");
            model.addAttribute("userEntity", user);
        } catch (NoSuchElementException e) {
            log.error("user not found " + e.getMessage());
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/update";
    }

    /**
     * Validate updating user page.
     * @param id user id
     * @param user user to update
     * @param result binding
     * @param model user list to pass
     * @return user list if user validated or return to user update page
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") final Integer id,
                             @Valid final UserEntity user,
                            final BindingResult result, final Model model) {
        log.info("POST/user/update/" + id);
        if (result.hasErrors()) {
            log.info("update error");
            return "user/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.update(user);
        log.info("update success");
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    /**
     * Delete user.
     * @param id user id
     * @param model user list to pass
     * @return redirect user list home page
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") final Integer id,
                             final Model model) {
        log.info("DEL/user/delete/" + id);
        userService.delete(id);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    /**
     * Get all user.
     * @return user list
     */
    @GetMapping("/user/")
    public ResponseEntity<List<UserEntity>> getAllUser() {
        log.info("GET/user");
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Get user by id.
     * @param userId id user
     * @return user
     */
    @GetMapping("/user/userId/{id}")
    public ResponseEntity<UserEntity> getUserById(
            @PathVariable("id") final Integer userId) {
        log.info("GET/user/userId/" + userId);
        try {
            return ResponseEntity.ok(userService.findById(userId));
        } catch (NoSuchElementException e) {
            log.error("GetUserById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a user.
     * @param userEntity user
     * @return user added
     */
    @PostMapping("/user/add")
    public ResponseEntity<UserEntity> adduser(
            @RequestBody final UserEntity userEntity) {
        log.info("POST/user/add");
        try {
            return ResponseEntity.ok(userService.save(userEntity));
        } catch (NoSuchElementException e) {
            log.error("addUser error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update user.
     * @param userEntity user new information
     * @return user updated
     */
    @PutMapping("/user")
    public ResponseEntity<UserEntity> updateuser(
            @RequestBody final UserEntity userEntity) {
        log.info("PUT/user/userId/ " + userEntity.getId());
        try {
            return ResponseEntity.ok(userService.update(userEntity));
        } catch (NoSuchElementException e) {
            log.error("UpdateUser error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a user by his id.
     * @param userId id user
     * @return user deleted
     */
    @DeleteMapping("/user/userId/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("id") final Integer userId) {
        log.info("DEL/user/userId/" + userId);
        try {
            userService.delete(userId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteUser error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
