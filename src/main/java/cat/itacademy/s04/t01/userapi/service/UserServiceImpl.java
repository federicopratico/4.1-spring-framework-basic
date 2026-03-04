package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dtos.UserDTO;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyPresentException;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(UserDTO user) {
        if(repository.existsByEmail(user.email()))
            throw new EmailAlreadyPresentException();

        UUID newUserId = UUID.randomUUID();
        User newUser = new User(newUserId, user.name(), user.email());
        return repository.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> searchUsersByName(String name) {
        return repository.searchByName(name);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return repository.existsByEmail(email);
    }
}
