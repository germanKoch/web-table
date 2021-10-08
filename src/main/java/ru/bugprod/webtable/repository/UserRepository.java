package ru.bugprod.webtable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.bugprod.webtable.repository.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> getByUsernameAndPassword(String username, String password);

    Optional<User> getByToken(String token);

}
