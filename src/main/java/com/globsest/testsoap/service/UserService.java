package com.globsest.testsoap.service;

import com.globsest.testsoap.entity.User;
import com.globsest.testsoap.entity.UserRole;
import com.globsest.testsoap.repository.UserRepository;
import com.globsest.testsoap.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private UserRoleRepository userRoleRepository;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }


    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteUserById(Long id) {
        if (userRoleRepository.existsById(id)) {
            userRoleRepository.deleteById(id);
            return true;
        } else {return false;}
    }

    public User createUser(String name, String login, String password, List<String> roleNames) {
        Set<UserRole> roles = resolveRoles(roleNames);
        User user = new User(name, login, password, roles);
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, String name, String login, String password, List<String> roleNames) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return Optional.empty();

        User user = userOpt.get();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        if (roleNames != null) {
            Set<UserRole> roles = resolveRoles(roleNames);
            user.setRoles(roles);
        }

        return Optional.of(userRepository.save(user));
    }

    private Set<UserRole> resolveRoles(List<String> roleNames) {
        Set<UserRole> roles = new HashSet<>();
        for (String roleName : roleNames) {
            userRoleRepository.findByName(roleName)
                    .ifPresent(roles::add);
        }
        return roles;
    }



}
