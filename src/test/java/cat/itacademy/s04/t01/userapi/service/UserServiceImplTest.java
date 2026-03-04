package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dtos.UserDTO;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyPresentException;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void createUser_shouldReturnCreatedUserWhenEmailNotExists() {
        UserDTO userDto = new UserDTO("John Doe", "john@example.com");

        when(repository.existsByEmail(userDto.email())).thenReturn(false);
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = service.createUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.name(), result.name());
        assertEquals(userDto.email(), result.email());
        assertNotNull(result.id());  // UUID was generated
        verify(repository).existsByEmail(userDto.email());
        verify(repository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowWhenEmailAlreadyExists() {
        UserDTO userDto = new UserDTO("John", "john@example.com");
        when(repository.existsByEmail(userDto.email())).thenReturn(true);

        assertThrows(EmailAlreadyPresentException.class, () -> service.createUser(userDto));
        verify(repository).existsByEmail(userDto.email());
        verify(repository, never()).save(any());
    }

    @Test
    void getAllUsers_shouldReturnEmptyListWhenNoUsers() {
        when(repository.findAll()).thenReturn(List.of());

        List<User> result = service.getAllUsers();

        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        User user1 = new User(UUID.randomUUID(), "John", "john@example.com");
        User user2 = new User(UUID.randomUUID(), "Jane", "jane@example.com");
        when(repository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = service.getAllUsers();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void getUserById_shouldReturnUserWhenExists() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "John", "john@example.com");
        when(repository.findById(id)).thenReturn(Optional.of(user));

        User result = service.getUserById(id);

        assertEquals(user, result);
        verify(repository).findById(id);
    }

    @Test
    void getUserById_shouldThrowExceptionWhenNotExists() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.getUserById(id));
        verify(repository).findById(id);
    }

    @Test
    void searchUsersByName_shouldReturnMatchingUsers() {
        User user = new User(UUID.randomUUID(), "John Doe", "john@example.com");
        when(repository.searchByName("jo")).thenReturn(List.of(user));

        List<User> result = service.searchUsersByName("jo");

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(repository).searchByName("jo");
    }

    @Test
    void searchUsersByName_shouldReturnEmptyWhenNoMatches() {
        when(repository.searchByName("xyz")).thenReturn(List.of());

        List<User> result = service.searchUsersByName("xyz");

        assertTrue(result.isEmpty());
        verify(repository).searchByName("xyz");
    }

    @Test
    void isEmailTaken_shouldReturnTrueWhenExists() {
        when(repository.existsByEmail("john@example.com")).thenReturn(true);

        boolean result = service.isEmailTaken("john@example.com");

        assertTrue(result);
        verify(repository).existsByEmail("john@example.com");
    }

    @Test
    void isEmailTaken_shouldReturnFalseWhenNotExists() {
        when(repository.existsByEmail("notfound@example.com")).thenReturn(false);

        boolean result = service.isEmailTaken("notfound@example.com");

        assertFalse(result);
        verify(repository).existsByEmail("notfound@example.com");
    }
}