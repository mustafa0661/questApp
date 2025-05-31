package com.project.guestApp.services;

import com.project.guestApp.entities.Role;
import com.project.guestApp.entities.User;
import com.project.guestApp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            foundUser.setPassword(encodedPassword);
            userRepository.save(foundUser);
            return foundUser;
        } else return null;
    }

    public void deleteOneUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User assignRoleToUser(Long userId, Role role) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.getRoles().add(role);
            return userRepository.save(user);
        } else return null;
    }
}
