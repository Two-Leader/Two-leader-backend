package com.twoleader.backend.domain.studyRoom.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudyRoomRepositoryTest {
  @Autowired private StudyRoomRepository studyRoomRepository;

  private List<StudyRoom> studyRooms = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    studyRooms.add(
        studyRoomRepository.save(StudyRoom.builder().roomName("TestStudyRoom1").build()));
    studyRooms.add(
        studyRoomRepository.save(StudyRoom.builder().roomName("TestStudyRoom2").build()));
  }

  @Test
  @DisplayName("StudyRoom 모두조회 Test")
  public void findAllStudyRoomTest() {
    // given
    int index = 0;
    StudyRoom studyRoom1 = studyRooms.get(index);

    // when
    List<StudyRoom> findStudyRooms = studyRoomRepository.findAll();

    // then
    assertEquals(studyRooms.size(), findStudyRooms.size());
    assertEquals(studyRoom1.getStudyRoomId(), findStudyRooms.get(index).getStudyRoomId());
    assertEquals(studyRoom1.getRoomUuid(), findStudyRooms.get(index).getRoomUuid());
  }

  @Test
  @DisplayName("존재하는 StudyRoom UUID로 조회 Test")
  public void findStudyRoomByUuidTestWhenExist() {
    // given
    int index = 0;
    StudyRoom studyRoom = studyRooms.get(index);

    // when
    Optional<StudyRoom> findStudyRoom =
        studyRoomRepository.findStudyRoomByUuid(studyRoom.getRoomUuid());

    // then
    assertEquals(studyRoom.getRoomUuid(), findStudyRoom.get().getRoomUuid());
    assertEquals(studyRoom.getRoomName(), findStudyRoom.get().getRoomName());
    assertEquals(studyRoom.getStudyRoomId(), findStudyRoom.get().getStudyRoomId());
  }
}
