package com.globsest.testsoap.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationService {

    public List<String> validateUser(String login, String name, String password) {
        List<String> errors = new ArrayList<>();

        if (login == null || login.isBlank()) {
            errors.add("Нужно заполнить логин");
        }

        if (name == null || name.isBlank()) {
            errors.add("Нужно заполнить имя");
        }

        if (password == null || password.isBlank()) {
            errors.add("Нужно заполнить пароль");
        } else {
            if (password.length() < 8) {
                errors.add("Пароль должен содерджать не менее 8 символов");
            }

            if (!password.matches(".*[A-Z].*")) {
                errors.add("В пароле должна сождержаться хотя бы одна заглавная буква");
            }


            if (!password.matches(".*\\d.*")) {
                errors.add("В пароле должна сождержаться хотя бы одна цифра");
            }
        }

        return errors;
    }
}
