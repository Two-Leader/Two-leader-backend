package com.twoleader.backend.domain.studyRoom.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.repository.UserRepository;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
  @Autowired private UserRepository userRepository;
  @Autowired private RoomUserRepository roomUserRepository;

  @PersistenceContext EntityManager em;

  private List<StudyRoom> studyRooms = new ArrayList<>();
  private List<User> users = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    users.add(
        userRepository.save(
            User.builder()
                .userUuid(UUID.randomUUID())
                .email("tester@gmail.com")
                .password("password")
                .nickName("tester")
                .build()));
    users.add(
        userRepository.save(
            User.builder()
                .userUuid(UUID.randomUUID())
                .email("tester2@gmail.com")
                .password("password")
                .nickName("tester2")
                .build()));
    studyRooms.add(
        studyRoomRepository.save(
            StudyRoom.builder()
                .roomName("TestStudyRoom1")
                .information("testRoomInformation")
                .password("testPassword")
                .totalNop(5)
                .constructor(users.get(0))
                .build()));
    studyRooms.add(
        studyRoomRepository.save(
            StudyRoom.builder()
                .roomName("TestStudyRoom2")
                .information("testRoomInformation")
                .totalNop(5)
                .constructor(users.get(0))
                .build()));
    studyRooms.add(
        studyRoomRepository.save(
            StudyRoom.builder()
                .roomName("TestStudyRoom2")
                .totalNop(5)
                .constructor(users.get(0))
                .build()));
  }

  @Test
  @DisplayName("StudyRoom 모두조회 Test")
  public void findAllStudyRoomTest() {
    // given
    int index = 0;
    StudyRoom studyRoom = studyRooms.get(index);

    // when
    List<StudyRoom> findStudyRooms = studyRoomRepository.findAll();

    // then
    assertEquals(studyRooms.size(), findStudyRooms.size());
    assertEquals(studyRoom.getStudyRoomId(), findStudyRooms.get(index).getStudyRoomId());
    assertNull(findStudyRooms.get(1).getPassword());
    assertNull(findStudyRooms.get(2).getInformation());
  }

  @Test
  @DisplayName("존재하는 StudyRoom UUID로 조회 Test")
  public void findStudyRoomByUuidTestWhenExist() {
    // given
    int index = 0;
    StudyRoom studyRoom = studyRooms.get(index);

    // when
    Optional<StudyRoom> findStudyRoom = studyRoomRepository.findByRoomUuid(studyRoom.getRoomUuid());

    // then
    assert findStudyRoom.isPresent();
    assertEquals(studyRoom.getRoomUuid(), findStudyRoom.get().getRoomUuid());
  }

  @Test
  @DisplayName("users N+1 문제 Test")
  public void UsersNPlusOneTest() {
    // given
    roomUserRepository.save(
        RoomUser.builder()
            .studyRoom(studyRooms.get(0))
            .roomUserName("tester")
            .user(users.get(0))
            .build());
    roomUserRepository.save(
        RoomUser.builder()
            .studyRoom(studyRooms.get(0))
            .roomUserName("tester2")
            .user(users.get(1))
            .build());
    em.flush();
    em.clear();

    StudyRoom findStudyRoom =
        studyRoomRepository
            .findWithRoomUsersByRoomUuid(studyRooms.get(0).getRoomUuid())
            .orElseThrow();
    for (RoomUser roomUser : findStudyRoom.getRoomUsers()) {
      System.out.println(roomUser.getUser().getUserUuid());
    }
  }

  @Test
  @DisplayName("cascade Test")
  public void cascadeTest(){
    //given
    roomUserRepository.save(RoomUser.builder().roomUserName("tester").studyRoom(studyRooms.get(0)).user(users.get(0)).build());
    roomUserRepository.save(RoomUser.builder().roomUserName("tester1").studyRoom(studyRooms.get(0)).user(users.get(1)).build());
    em.flush();
    em.clear();
    StudyRoom studyRoom = studyRoomRepository.findById(studyRooms.get(0).getStudyRoomId()).orElseThrow();

    //when
    studyRoomRepository.delete(studyRoom);

    //then
    List<RoomUser> roomUsers = roomUserRepository.findAll();
    assertEquals(0,roomUsers.size());
  }
}
