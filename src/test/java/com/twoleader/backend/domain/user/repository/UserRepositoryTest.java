package com.twoleader.backend.domain.user.repository;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private List<User> users = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        users.add(
                userRepository.save(User.builder().email("testEmail1").password("testPassword").nickName("tester1").build()));
        users.add(
                userRepository.save(User.builder().email("testEmail2").password("testPassword").nickName("tester2").build()));
    }

    @DisplayName("email로 User찾기 Test")
    @Nested
    class findByEmailTest{
        @DisplayName("존재하는 User일 경우")
        @Test
        public void whenExistedUser(){
            //given
            int index = 0;
            User user = users.get(index);
            String email = user.getEmail();

            //when
            Optional<User> foundUser = userRepository.findByEmail(email);

            //then
            assertTrue(foundUser.isPresent());
        }

        @DisplayName("존재하지 않는 User일 경우")
        @Test
        public void whenNotExistedUser(){
            //given
            String email = "notExistedUserEmail";

            //when
            Optional<User> user = userRepository.findByEmail(email);

            //then
            assertTrue(user.isEmpty());
        }
    }

    @DisplayName("login Test")
    @Nested
    class loginTest{

        @DisplayName("존재하는 User일 경우")
        @Test
        public void whenExistedUser(){
            //given
            int index = 0;
            User user = users.get(index);

            //when
            Optional<User> foundUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

            //then
            assertTrue(foundUser.isPresent());
        }

        @DisplayName("존재하지 않는 User일 경우")
        @Test
        public void whenNotExistedUser(){
            //given
            String email = "notExistedUserEmail";
            String password = "notExistedUserPassword";

            //when
            Optional<User> user = userRepository.findByEmailAndPassword(email,password);

            //then
            assertTrue(user.isEmpty());
        }
    }

    @DisplayName("UUID로 User찾기")
    @Nested
    class findUserByUserUuid{

        @DisplayName("존재하는 User일 경우")
        @Test
        public void whenExistedUser(){
            //given
            UUID userUuid = users.get(0).getUserUuid();

            //when
            Optional<User> user = userRepository.findByUserUuid(userUuid);

            //then
            assertTrue(user.isPresent());
        }
        @DisplayName("존재하지 않는 User일 경우")
        @Test
        public void whenNotExistedUser(){
            //given
            UUID userUuid = UUID.randomUUID();

            //when
            Optional<User> user = userRepository.findByUserUuid(userUuid);

            //then
            assertTrue(user.isEmpty());
        }
    }

}
