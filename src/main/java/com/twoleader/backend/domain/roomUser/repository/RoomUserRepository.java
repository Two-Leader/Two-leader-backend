package com.twoleader.backend.domain.roomUser.repository;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {

  @Query("SELECT u FROM RoomUser u JOIN FETCH u.studyRoom s WHERE s.roomUuid = :roomUuid")
  List<RoomUser> findAllInStudyRoomByStudyRoomUuid(@Param("roomUuid") UUID roomUuid);
}
