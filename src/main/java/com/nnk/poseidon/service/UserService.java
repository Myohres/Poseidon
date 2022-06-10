package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.domain.UserEntity;
import com.nnk.poseidon.repositories.BidListRepository;
import com.nnk.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(Integer id) {
        return  userRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("No User found with id : " + id));
    }

    public UserEntity add(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity update(UserEntity userEntity1) {
        UserEntity userEntity = findById(userEntity1.getId());
        userEntity.setPassword(userEntity1.getPassword());
        return userRepository.save(userEntity);
    }

    public void delete(Integer id) {
        UserEntity userEntity = findById(id);
        userRepository.delete(userEntity);
    }
}
