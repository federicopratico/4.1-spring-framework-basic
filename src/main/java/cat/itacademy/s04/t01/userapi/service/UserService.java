package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dtos.UserDTO;
import cat.itacademy.s04.t01.userapi.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(UserDTO user);
    List<User> getAllUsers();
    User getUserById(UUID id);
    List<User> searchUsersByName(String name);
    boolean isEmailTaken(String email);
}
