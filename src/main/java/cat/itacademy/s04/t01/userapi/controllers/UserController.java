package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static final List<User> userDatabase = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAll() {
        return userDatabase;
    }

}
