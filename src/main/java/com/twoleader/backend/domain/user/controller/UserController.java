package com.twoleader.backend.domain.user.controller;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {
  private final UserService userService;

  @GetMapping("{room_uuid}/users")
  public String displayUserInfoPage(Model model, @PathVariable("room_uuid") UUID room_uuid) {
    model.addAttribute("request", CreateUserRequest.builder().build());
    model.addAttribute("room_uuid", room_uuid);
    return "user_info";
  }

  @PostMapping("{room_uuid}/users")
  public String createUser(
      @Valid CreateUserRequest request, @PathVariable("room_uuid") UUID room_uuid, Model model) {
    User user = userService.createUser(request, room_uuid);
    return "redirect:/studies/" + room_uuid + "/" + user.getUser_uuid();
  }
}
