package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsUserTest.getUser;
import static com.fiap.techchallenge.utils.UtilsUserTest.getUserList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = getUser();

        when(userRepository.save(any(User.class))).thenReturn(user);

        User userResponse = userRepository.save(user);

        assertEquals(user, userResponse);

    }

    @Test
    void testFindUserById() {
        User user = getUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> userResponse = userRepository.findById(1L);

        assertTrue(userResponse.isPresent());
        assertEquals(user, userResponse.get());
        assertThat(userResponse.get())
                .isNotNull()
                .usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void testFindUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> userResponse = userRepository.findById(1L);

        Assertions.assertFalse(userResponse.isPresent());
    }

    @Test
    void TestUserFindAll() {
        List<User> users = getUserList();

        when(userRepository.findAll()).thenReturn(users);

        List<User> userResponse = userRepository.findAll();

        assertEquals(users, userResponse);
    }

    @Test
    void testDeleteUserById() {
        User user = getUser();

        doNothing().when(userRepository).deleteById(any());

        userRepository.deleteById(user.id());

        verify(userRepository, times(1)).deleteById(any());

    }

}