package com.twoleader.backend.domain.user.repository;

import com.twoleader.backend.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE u.user_name = :user_name")
  Optional<User> findUserByUserName(@Param("user_name") String user_name);
}
