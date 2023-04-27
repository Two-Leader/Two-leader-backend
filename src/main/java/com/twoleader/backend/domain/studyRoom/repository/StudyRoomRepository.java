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
  @Query("SELECT s FROM StudyRoom s WHERE s.room_uuid = :room_uuid")
  Optional<StudyRoom> findStudyRoomByRoom_uuid(@Param("room_uuid") UUID room_uuid);
}
