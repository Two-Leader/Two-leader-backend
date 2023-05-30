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
  @Query("SELECT u FROM User u WHERE u.userUuid = :user_uuid")
  Optional<User> findUserByUserUuid(@Param("user_uuid") UUID user_uuid);

  @Query("SELECT u FROM User u JOIN FETCH u.studyRoom s WHERE s.roomUuid = :roomUuid")
  List<User> findAllInStudyRoomByStudyRoomUuid(@Param("roomUuid") UUID roomUuid);

  @Query("SELECT COUNT(u) > 1 FROM User u JOIN u.studyRoom r WHERE r.roomUuid = :roomUuid")
  boolean checkUsersByRoomUuid(@Param("roomUuid") UUID roomUuid);

  @Query("DELETE FROM User u WHERE u.userUuid = :userUuid")
  void deleteByUuid(@Param("userUuid") UUID userUuid);
}
