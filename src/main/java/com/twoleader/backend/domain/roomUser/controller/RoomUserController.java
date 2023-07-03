package com.twoleader.backend.domain.roomUser.controller;

import static com.twoleader.backend.global.result.api.ResultCode.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.service.RoomUserService;
import com.twoleader.backend.global.result.api.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/room-users")
@RestController
public class RoomUserController {
  private final RoomUserService roomUserService;

  @Operation(summary = "User 생성 요청", description = "User를 생성합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "CREATED(성공)"),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @PostMapping("/{roomUuid}")
  public ResponseEntity<EntityModel<ResultResponse>> createUser(
      @NotBlank @PathVariable("roomUuid") UUID roomUuid,
      @Valid @RequestBody CreateRoomUserRequest request) {
    GetRoomUserResponse response = roomUserService.createUser(roomUuid, request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            EntityModel.of(
                new ResultResponse<>(API_SUCCESS_ROOM_USER_REGISTRATION, response),
                linkTo(methodOn(RoomUserController.class).createUser(roomUuid, request))
                    .withSelfRel()));
  }

  @Operation(summary = "User 조회 요청", description = "한 User을 조회합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK(성공)"),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @GetMapping("/{roomUserId}")
  public ResponseEntity<EntityModel<ResultResponse<GetRoomUserResponse>>> getUser(
      @NotNull @PathVariable("roomUserId") long userId) {
    GetRoomUserResponse response = roomUserService.getUser(userId);
    return ResponseEntity.ok(
        EntityModel.of(
            new ResultResponse<>(API_SUCCESS_ROOM_USER_GET, response),
            linkTo(methodOn(RoomUserController.class).getUser(userId)).withSelfRel()));
  }

  @Operation(summary = "User 삭제 요청", description = "User을 삭제합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK(성공)"),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @DeleteMapping("/{roomUserId}")
  public ResponseEntity<EntityModel<ResultResponse>> deleteUser(
      @PathVariable("roomUserId") long userId) {
    roomUserService.deleteUserById(userId);
    return ResponseEntity.ok(
        EntityModel.of(
            new ResultResponse<>(API_SUCCESS_ROOM_USER_DELETE),
            linkTo(methodOn(RoomUserController.class).deleteUser(userId)).withSelfRel()));
  }
}
