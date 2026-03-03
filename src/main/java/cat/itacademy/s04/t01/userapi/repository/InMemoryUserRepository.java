package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private static final List<User> userDatabase = new ArrayList<>();

    @Override
    public User save(User user) {
        userDatabase.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(userDatabase);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDatabase.stream()
                .filter(user -> user.id().equals(id))
                .findFirst();
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

    // test purpose method
    public void clear() {
        userDatabase.clear();
    }
}
