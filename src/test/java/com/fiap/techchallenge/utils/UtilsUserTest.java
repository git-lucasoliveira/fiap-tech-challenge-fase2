package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class UtilsTest {

    public static User getUser() {
        return new User(
                1L,
                "joana",
                "joana@gmail.com",
                "joana123",
                "123456",
                1L,
                1L,
                LocalDateTime.now()
        );
    }

    public static List<User> getUserList(){
        return List.of(
                new User(
                        1L,
                        "joana",
                        "joana@gmail.com",
                        "joana123",
                        "123456",
                        1L,
                        1L,
                        LocalDateTime.now()
                )
        );
    }
}
