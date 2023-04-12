package com.twoLeader.twoLeader.domain.studyRoom.repository;

import com.twoLeader.twoLeader.domain.studyRoom.entity.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
}
