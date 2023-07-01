package com.twoleader.backend.domain.studyRoom.repository;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {


  @Query("SELECT DISTINCT s FROM StudyRoom s JOIN FETCH s.roomUsers ru JOIN FETCH ru.user u WHERE s.roomUuid = :roomUuid")
  Optional<StudyRoom> findWithRoomUsersByRoomUuid(@Param("roomUuid") UUID roomUuid);

  Optional<StudyRoom> findByRoomUuid(UUID roomUuid);
}
