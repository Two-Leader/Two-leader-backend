package com.twoleader.backend.domain.studyRoom.controller;

import static com.twoleader.backend.global.result.ResultCode.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("studies")
@Controller
public class StudyRoomController {

  private final StudyRoomService studyRoomService;

  @PostMapping("")
  public String createStudyRoom(
          @Valid CreateStudyRoomRequest request) {
    studyRoomService.createStudyRoom(request);
    return "redirect:/";
  }

  @GetMapping("/{room_uuid}")
  public String getStudyRoom(Model model, @PathVariable("room_uuid")UUID room_uuid){
    GetStudyRoomResponse response = studyRoomService.findStudyRoomByRoom_uuid(room_uuid);
    model.addAttribute("response",response);

    return "chat_room";
  }
}
