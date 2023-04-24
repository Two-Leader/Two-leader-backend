package com.twoleader.backend.domain.user.controller;

import static com.twoleader.backend.global.result.ResultCode.GET_USER_SUCCESS;
import static com.twoleader.backend.global.result.ResultCode.STUDYROOM_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.service.UserService;
import com.twoleader.backend.global.result.ResultResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
  private final UserService userService;

  @Operation(
          summary = "User 생성 요청",
          description = "User를 생성합니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "201", description = "CREATED(성공)"),
          @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
          @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @PostMapping
  public ResponseEntity<EntityModel<ResultResponse>> createUser(
      @Valid @RequestBody CreateUserRequest request) {
    userService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            EntityModel.of(
                new ResultResponse<>(STUDYROOM_REGISTRATION_SUCCESS),
                linkTo(methodOn(UserController.class).createUser(request)).withSelfRel()));
  }

  @Operation(
          summary = "User 조회 요청",
          description = "한 User을 조회합니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "OK(성공)"),
          @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
          @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @GetMapping
  public ResponseEntity<EntityModel<ResultResponse<GetUserResponse>>> getUser(
      @NotBlank @RequestBody GetUserRequest request) {
    GetUserResponse user = userService.getUser(request);
    return ResponseEntity.ok(
        EntityModel.of(
            new ResultResponse<>(GET_USER_SUCCESS, user),
            linkTo(methodOn(UserController.class).getUser(request)).withSelfRel()));
  }
}
