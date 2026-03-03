package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {

    private static final List<User> userDatabase = new ArrayList<>();

    @Override
    public User save(User user) {
        userDatabase.add(user);
        int index = userDatabase.indexOf(user);
        return userDatabase.get(index);
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(userDatabase);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(userDatabase.stream()
                .filter(user -> user.id().equals(id))
                .findFirst()
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public List<User> searchByName(String name) {
        return userDatabase.stream()
                .filter(user -> user.name().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDatabase.stream()
                .anyMatch(user -> user.email().equalsIgnoreCase(email));
    }
}
