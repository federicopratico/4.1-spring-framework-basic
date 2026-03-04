package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dtos.UserDTO;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@RequestBody UserDTO userDTO) {
        return service.createUser(userDTO);
    }

    @GetMapping("/users")
    public List<User> getAll(@RequestParam(required = false) String name) {
        if(name != null && !name.isBlank()) {
            return service.searchUsersByName(name);
        }

        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getByID(@PathVariable UUID id) {
        return service.getUserById(id);
    }

}
