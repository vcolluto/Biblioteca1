package org.java.lessons.biblioteca.repository;

import java.util.Optional;

import org.java.lessons.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

}
