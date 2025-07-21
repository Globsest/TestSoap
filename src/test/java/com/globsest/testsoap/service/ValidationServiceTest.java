package com.globsest.testsoap.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {

    private final ValidationService validationService = new ValidationService();

    @Test
    void testValidateUser_withValidData_shouldReturnEmptyList() {
        List<String> errors = validationService.validateUser("login", "name", "Password1");
        assertTrue(errors.isEmpty());
    }

    @Test
    void testValidateUser_withMissingFields_shouldReturnErrors() {
        List<String> errors = validationService.validateUser("", "", "");
        assertTrue(errors.contains("Нужно заполнить логин"));
        assertTrue(errors.contains("Нужно заполнить имя"));
        assertTrue(errors.contains("Нужно заполнить пароль"));
    }

    @Test
    void testValidateUser_withInvalidPassword_shouldReturnErrors() {
        List<String> errors = validationService.validateUser("login", "name", "pass");
        assertTrue(errors.stream().anyMatch(s -> s.contains("8 символов")));
        assertTrue(errors.stream().anyMatch(s -> s.contains("заглавная буква")));
        assertTrue(errors.stream().anyMatch(s -> s.contains("одна цифра")));
    }
}

