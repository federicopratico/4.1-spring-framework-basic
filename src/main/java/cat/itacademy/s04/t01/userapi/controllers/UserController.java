package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dtos.UserDTO;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private static final List<User> userDatabase = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAll() {
        return userDatabase;
    }

    @PostMapping("/users")
    public User add(@RequestBody UserDTO userDTO) {
        UUID newUserId = UUID.randomUUID();
        String newUserName = userDTO.name();
        String newUserEmail = userDTO.email();

        User newUser = new User(newUserId, newUserName, newUserEmail);
        userDatabase.add(newUser);

        return newUser;
    }

}
