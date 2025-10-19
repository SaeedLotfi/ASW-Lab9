package edu.ads.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ads.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}