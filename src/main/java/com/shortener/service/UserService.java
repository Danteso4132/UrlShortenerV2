package com.shortener.service;

import com.shortener.entity.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(User man);

    User saveAdmin(User man);

    void deleteUserById(UUID id);

    User getUser(UUID id);

    void deleteUser(UUID id);

    User getUserByLogin(String login);

    boolean isStringOnlyAlphabet(String str);

    boolean isStringOnlyAlphabetAndNumbersAndSymbols(String str);


}
