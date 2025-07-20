package com.globsest.testsoap.controller;

import com.example.soapusers.wsdl.*;
import com.globsest.testsoap.entity.User;
import com.globsest.testsoap.service.UserService;
import com.globsest.testsoap.service.ValidationService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Endpoint
public class UserController {

    private static final String NAMESPACE_URI = "http://globsest.com/testsoap";

    private final UserService userService;
    private final ValidationService validationService;

    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers() {
        List<User> users = userService.getAllUsers();
        GetAllUsersResponse response = new GetAllUsersResponse();

        for (User user : users) {
            UserShort user1 = new UserShort();
            user1.setId(user.getId());
            user1.setName(user.getName());
            user1.setLogin(user.getLogin());
            response.getUsers().add(user1);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        Optional<User> userOpt = userService.getUserById(request.getId());
        GetUserResponse response = new GetUserResponse();

        userOpt.ifPresent(user -> {
            UserFull userFull = new UserFull();
            userFull.setId(user.getId());
            userFull.setName(user.getName());
            userFull.setLogin(user.getLogin());
            userFull.setPassword(user.getPassword());
            userFull.getRoles().addAll(
                    user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList())
            );
            response.setUser(userFull);
        });

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        boolean deleted = userService.deleteUserById(request.getId());
        DeleteUserResponse response = new DeleteUserResponse();
        response.setSuccess(deleted);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();

        List<String> errors = validationService.validateUser(request.getLogin(), request.getName(), request.getPassword());
        if (!errors.isEmpty()) {
            response.setSuccess(false);
            response.getErrors().addAll(errors);
            return response;
        }

        userService.createUser(
                request.getName(),
                request.getLogin(),
                request.getPassword(),
                request.getRoles()
        );

        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse response = new UpdateUserResponse();

        List<String> errors = validationService.validateUser(request.getLogin(), request.getName(), request.getPassword());
        if (!errors.isEmpty()) {
            response.setSuccess(false);
            response.getErrors().addAll(errors);
            return response;
        }

        Optional<User> updatedUser = userService.updateUser(
                request.getId(),
                request.getName(),
                request.getLogin(),
                request.getPassword(),
                request.getRoles()
        );

        if (updatedUser.isPresent()) {
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.getErrors().add("User not found");
        }

        return response;
    }
}
