package io.github.gomestg.domain.repository;

import io.github.gomestg.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByLogin(String login);
}
