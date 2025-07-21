package com.globsest.testsoap.service;

import com.globsest.testsoap.entity.User;
import com.globsest.testsoap.entity.Roles;
import com.globsest.testsoap.repository.UserRepository;
import com.globsest.testsoap.repository.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private RolesRepository rolesRepository;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
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
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {return false;}
    }

    public User createUser(String name, String login, String password, List<String> roleNames) {
        Set<Roles> roles = resolveRoles(roleNames);
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
            Set<Roles> roles = resolveRoles(roleNames);
            user.setRoles(roles);
        }

        return Optional.of(userRepository.save(user));
    }

    private Set<Roles> resolveRoles(List<String> roleNames) {
        Set<Roles> roles = new HashSet<>();
        for (String roleName : roleNames) {
            rolesRepository.findByName(roleName)
                    .ifPresent(roles::add);
        }
        return roles;
    }



}
