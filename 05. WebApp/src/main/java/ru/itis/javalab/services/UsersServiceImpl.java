package ru.itis.javalab.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<User> getAllByAge(Integer age) {
        return usersRepository.findAllByAge(age);
    }

    @Override
    public User getByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public boolean existUser(String username) {
        return usersRepository.userIsExist(username);
    }

    @Override
    public String generateSecurePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matchesPasswords(String password, String hashPassword) {
        return passwordEncoder.matches(password, hashPassword);
    }
}
