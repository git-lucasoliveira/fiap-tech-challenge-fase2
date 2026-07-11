package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.infrastructure.persistence.entity.UserJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsUserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryAdapterTest {

    @Mock
    private SpringDataUserRepository springDataUserRepository;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    void saveTest() {
        User user = getUser();

        when(springDataUserRepository.save(any(UserJpaEntity.class))).thenReturn(getUserJpaEntity());

        User userResponse = userRepositoryAdapter.save(user);

        assertEquals(user.id(), userResponse.id());
        assertThat(userResponse)
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void findByIdTest() {
        when(springDataUserRepository.findById(anyLong())).thenReturn(Optional.of(getUserJpaEntity()));

        Optional<User> userResponse = userRepositoryAdapter.findById(1L);

        assertThat(userResponse)
                .isNotNull()
                .isEqualTo(Optional.of(getUser()));
    }

    @Test
    void findAll(){
        when(springDataUserRepository.findAll()).thenReturn(getUserJpaEntityList());

        List<User> userResponse = userRepositoryAdapter.findAll();

        assertThat(userResponse)
                .isNotNull()
                .isEqualTo(getUserList())
                .isInstanceOf(Collection.class);
    }

    @Test
    void deleteById(){
        doNothing().when(springDataUserRepository).deleteById(any());

        userRepositoryAdapter.deleteById(1L);

        verify(springDataUserRepository, times(1)).deleteById(any());
    }
}
