package com.twoleader.backend.domain.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.NotFoundUser;
import com.twoleader.backend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = {"test"})
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    
    private static User user;
    private static StudyRoom studyRoom;
    
    @BeforeAll
    public static void setUp(){
        studyRoom = StudyRoom.builder()
                .room_id(1L)
                .room_uuid(UUID.randomUUID())
                .room_name("testStudyRoom")
                .build();
        
        user = User.builder()
                .user_id(1L)
                .user_uuid(UUID.randomUUID())
                .user_name("testUser")
                .room(studyRoom)
                .build();
    }

    @Nested
    @DisplayName("Uuid로 User찾기")
    class getUserByUserUuid {
        @Test
        @DisplayName("User 존재")
        public void getUserByUserUuidTestwhenUserExist() {
            //given
            given(userRepository.findUserByUserUuid(any())).willReturn(Optional.ofNullable(user));
            GetUserRequest request = GetUserRequest.builder()
                    .user_uuid(user.getUser_uuid())
                    .build();

            //when
            GetUserResponse response = userService.getUser(request);

            //then
            assertEquals(user.getUser_uuid(), response.getUser_uuid());
            assertEquals(user.getUser_name(), response.getUser_name());
        }

        @Test
        @DisplayName("존재하지 않는 User")
        public void getUserByUserUuidTestWhenUserNotExist(){
            //given
            given(userRepository.findUserByUserUuid(any())).willReturn(Optional.ofNullable(null));
            GetUserRequest request = GetUserRequest.builder()
                    .user_uuid(UUID.randomUUID())
                    .build();

            //when,then
            assertThrows(NotFoundUser.class,() -> {
                GetUserResponse response = userService.getUser(request);
            });

        }
    }
}
