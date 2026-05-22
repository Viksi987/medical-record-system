package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.User;
import bg.nbu.medicalrecordsystem.repository.UserRepository;
import bg.nbu.medicalrecordsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {

        User user = new User();

        user.setUsername("admin");

        user.setPassword("1234");

        when(passwordEncoder.encode("1234"))
                .thenReturn("encodedPassword");

        when(userRepository.save(user))
                .thenReturn(user);

        User savedUser =
                userService.saveUser(user);

        assertNotNull(savedUser);

        assertEquals(
                "encodedPassword",
                savedUser.getPassword()
        );
    }

    @Test
    void testFindByUsername() {

        User user = new User();

        user.setUsername("admin");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        User foundUser =
                userService.findByUsername("admin");

        assertNotNull(foundUser);

        assertEquals(
                "admin",
                foundUser.getUsername()
        );
    }
}