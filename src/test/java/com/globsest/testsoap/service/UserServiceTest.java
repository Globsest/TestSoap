package com.globsest.testsoap.service;

import com.globsest.testsoap.entity.User;
import com.globsest.testsoap.repository.RolesRepository;
import com.globsest.testsoap.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        rolesRepository = mock(RolesRepository.class);
        userService = new UserService(userRepository, rolesRepository);
    }

    @Test
    void testFindAllUsers() {
        List<User> users = List.of(new User("name", "login", "Password1", Set.of()));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        User user = new User("name", "login", "Password1", Set.of());
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("name", result.get().getName());
    }

    @Test
    void testDeleteUserById_Existing() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean result = userService.deleteUserById(1L);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserById_NotExisting() {
        when(rolesRepository.existsById(99L)).thenReturn(false);

        boolean result = userService.deleteUserById(99L);
        assertFalse(result);
        verify(rolesRepository, never()).deleteById(99L);
    }

}
