package com.twoleader.backend.domain.studyRoom.controller;

import static com.twoleader.backend.global.result.ResultCode.*;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
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

  @PostMapping("")
  public String createStudyRoom(@Valid CreateStudyRoomRequest request) {
    studyRoomService.createStudyRoom(request);
    return "redirect:/";
  }

  @GetMapping("/{room_uuid}")
  public String getStudyRoom(Model model, @PathVariable("room_uuid") UUID room_uuid) {
    GetStudyRoomResponse response = studyRoomService.findStudyRoomByRoom_uuid(room_uuid);
    model.addAttribute("response", response);

    return "chat_room";
  }
}
