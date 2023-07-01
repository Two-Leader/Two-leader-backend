package com.twoleader.backend.domain.user.repository;

import com.twoleader.backend.domain.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUserUuid(UUID userUuid);

  Optional<User> findByEmailAndPassword(String email, String password);

  boolean existsByEmail(String email);
}
