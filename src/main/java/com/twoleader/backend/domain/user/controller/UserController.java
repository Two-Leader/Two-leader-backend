package com.twoleader.backend.domain.user.controller;

import static com.twoleader.backend.global.result.ResultCode.GET_USER_SUCCESS;
import static com.twoleader.backend.global.result.ResultCode.STUDYROOM_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import com.twoleader.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {
  private final UserService userService;

  @GetMapping("/{room_uuid}")
  public String displayUserInfoPage(Model model, @PathVariable("room_uuid")UUID room_uuid){
    model.addAttribute("request",CreateUserRequest.builder().build());
    model.addAttribute("room_uuid",room_uuid);
    return "user_info";
  }

  @PostMapping("")
  public String createUser(
          @Valid CreateUserRequest request, Model model) {
    User user = userService.createUser(request);
    model.addAttribute("user",user);
    return "chat_room";
  }
}
