package ru.itis.javalab.services;

import ru.itis.javalab.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<User> getAllUsers();
    List<User> getAllByAge(Integer age);
    User getByUsername(String username);
    void deleteById(Long id);
    Optional<User> getById(Long id);
    boolean existUser(String username);
    String generateSecurePassword(String password);
    boolean matchesPasswords(String password, String hashPassword);
}
