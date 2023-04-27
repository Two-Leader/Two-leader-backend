package com.twoleader.backend.domain.user.repository;

import com.twoleader.backend.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE u.user_uuid = :user_uuid")
  Optional<User> findUserByUserUuid(@Param("user_uuid") UUID user_uuid);

  @Query("SELECT u FROM User u JOIN FETCH u.room WHERE u.room.room_uuid =:room_uuid")
  List<User> findAllByRoom_uuid(@Param("room_uuid") UUID room_uuid);
}
