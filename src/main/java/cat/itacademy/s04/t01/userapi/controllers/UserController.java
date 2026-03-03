package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dtos.UserDTO;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private static final List<User> userDatabase = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAll(@RequestParam(required = false) String name) {
        if(name != null && !name.isBlank()) {
            return userDatabase.stream()
                    .filter(user -> user.name().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }

        return userDatabase;
    }

    @GetMapping("/users/{id}")
    public User getByID(@PathVariable UUID id) {
        return userDatabase.stream()
                .filter(user -> user.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("user not found"));
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
