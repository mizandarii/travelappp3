package com.example.travel.service;

import com.example.travel.model.User;
import com.example.travel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser (Long userId){
        userRepository.deleteById(userId);

    }
    private BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    public void saveUser(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

}

