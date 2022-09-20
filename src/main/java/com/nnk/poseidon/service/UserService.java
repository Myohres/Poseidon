package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.UserEntity;
import com.nnk.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        UserEntity userEntity =
                userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No user with : " + username));
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority(userEntity.getRole());
        return new User(userEntity.getUsername(), userEntity.getPassword(),
                Collections.singletonList(simpleGrantedAuthority));
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(final Integer id) {
        return  userRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Invalid user Id:" + id));
    }

    public UserEntity save(final UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity update(final UserEntity userEntity1) {
        UserEntity userEntity = findById(userEntity1.getId());
        userEntity.setPassword(userEntity1.getPassword());
        userEntity.setRole(userEntity1.getRole());
        return userRepository.save(userEntity);
    }

    public void delete(final Integer id) {
        UserEntity userEntity = findById(id);
        userRepository.delete(userEntity);
    }
}
