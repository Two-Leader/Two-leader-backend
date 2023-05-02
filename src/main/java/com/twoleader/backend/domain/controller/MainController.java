package com.twoleader.backend.domain.controller;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MainController {
  private final StudyRoomService studyRoomService;

  @GetMapping("")
  public String displayMainPage(Model model) {
    model.addAttribute("request", CreateStudyRoomRequest.builder().build());
    model.addAttribute("rooms", studyRoomService.findAllStudyRoom());

    return "main";
  }
}
