package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        userRepository.clear();
    }

    @Test
    void save_shouldReturnSavedUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "John Doe", "john@example.com");

        User savedUser = userRepository.save(user);

        assertEquals(user, savedUser);
    }

    @Test
    void save_shouldPersistSavedUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "John Doe", "john@example.com");

        userRepository.save(user);

        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user, users.getFirst());
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoUsers() {
        List<User> users = userRepository.findAll();

        assertTrue(users.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllUser() {
        UUID id0 = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        User user0 = new User(id0, "John Doe", "john@example.com");
        User user1 = new User(id1, "Jane Doe", "jane@example.com");

        userRepository.save(user0);
        userRepository.save(user1);

        List<User> users = userRepository.findAll();

        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
        assertTrue(users.contains(user0));
        assertTrue(users.contains(user1));
    }

    @Test
    void findById_shouldReturnUserWhenExists() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "John Doe", "john@example.com");

        userRepository.save(user);

        Optional<User> result = userRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenUserNotExists() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<User> result = userRepository.findById(nonExistentId);

        assertTrue(result.isEmpty());
    }

    @Test
    void searchByName_shouldReturnMatchingUsers() {
        UUID id0 = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        User user0 = new User(id0, "John Doe", "john@example.com");
        User user1 = new User(id1, "Jane Doe", "jane@example.com");

        userRepository.save(user0);
        userRepository.save(user1);

        List<User> result = userRepository.searchByName("jo");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(user0, result.getFirst());
    }

    @Test
    void searchByName_shouldReturnEmptyIfNoMatch() {
        UUID id0 = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        User user0 = new User(id0, "John Doe", "john@example.com");
        User user1 = new User(id1, "Jane Doe", "jane@example.com");

        userRepository.save(user0);
        userRepository.save(user1);

        List<User> result = userRepository.searchByName("rrr");

        assertTrue(result.isEmpty());
    }

    @Test
    void searchByName_shouldReturnAllMatchedUsers() {
        UUID id0 = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        User user0 = new User(id0, "John Doe", "john@example.com");
        User user1 = new User(id1, "Jane Doe", "jane@example.com");
        User user2 = new User(id2, "Mojo Leaf", "mojo@example.com");

        userRepository.save(user0);
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> result = userRepository.searchByName("jo");

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(user0));
        assertTrue(result.contains(user2));
    }

    @Test
    void existsByEmail_shouldReturnTrueWhenUserExists() {
        UUID id0 = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        User user0 = new User(id0, "John Doe", "john@example.com");
        User user1 = new User(id1, "Jane Doe", "jane@example.com");

        userRepository.save(user0);
        userRepository.save(user1);

        boolean result = userRepository.existsByEmail("john@example.com");

        assertTrue(result);
    }

    @Test
    void existsByEmail_shouldReturnFalseWhenUserNotExists() {
        UUID id0 = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        User user0 = new User(id0, "John Doe", "john@example.com");
        User user1 = new User(id1, "Jane Doe", "jane@example.com");

        userRepository.save(user0);
        userRepository.save(user1);

        boolean result = userRepository.existsByEmail("mojo@example.com");

        assertFalse(result);
    }
}