package ru.bugprod.webtable.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bugprod.webtable.model.exception.LoginException;
import ru.bugprod.webtable.repository.UserRepository;
import ru.bugprod.webtable.repository.entity.User;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserUsecase {

    private final UserRepository repo;

    public String login(String username, String password) {
        var user = repo.getByUsernameAndPassword(username, password).orElseThrow(() -> {
            throw new LoginException("Invalid credentials");
        });
        var token = UUID.randomUUID().toString();
        user.setToken(token);
        repo.save(user);

        return token;
    }

    public Optional<User> findByToken(String token) {
        return repo.getByToken(token);
    }

}
