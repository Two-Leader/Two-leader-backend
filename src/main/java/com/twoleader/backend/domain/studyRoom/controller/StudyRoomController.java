package com.twoleader.backend.domain.studyRoom.controller;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.service.UserService;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("studies")
@Controller
public class StudyRoomController {

  private final StudyRoomService studyRoomService;
  private final UserService userService;

  @PostMapping("")
  public String createStudyRoom(@Valid CreateStudyRoomRequest request) {
    studyRoomService.createStudyRoom(request);
    return "redirect:/";
  }

  @GetMapping("/{room_uuid}/{user_uuid}")
  public String getStudyRoom(
      Model model,
      @PathVariable("room_uuid") UUID room_uuid,
      @PathVariable("user_uuid") UUID user_uuid) {
    GetStudyRoomResponse studyRoom = studyRoomService.findStudyRoomByRoom_uuid(room_uuid);
    GetUserResponse user =
        userService.getUser(GetUserRequest.builder().user_uuid(user_uuid).build());
    model.addAttribute("studyRoom", studyRoom);
    model.addAttribute("user", user);
    return "chat_room";
  }
}
