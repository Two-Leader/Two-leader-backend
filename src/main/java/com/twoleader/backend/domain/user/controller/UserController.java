package com.twoleader.backend.domain.user.controller;

import com.twoleader.backend.domain.roomUser.controller.RoomUserController;
import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.LoginRequest;
import com.twoleader.backend.domain.user.service.UserService;
import com.twoleader.backend.global.result.api.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.util.UUID;

import static com.twoleader.backend.global.result.api.ResultCode.API_SUCCESS_LOGIN_USER;
import static com.twoleader.backend.global.result.api.ResultCode.USER_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @Operation(summary = "User 생성 요청", description = "User를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED(성공)"),
            @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
    })
    @PostMapping("/signUp")
    public ResponseEntity<EntityModel<ResultResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        EntityModel.of(
                                new ResultResponse<>(USER_REGISTRATION_SUCCESS),
                                linkTo(methodOn(UserController.class).createUser(request)).withSelfRel()));
    }

    @Operation(summary = "User 로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS(성공)"),
            @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
    })
    @PostMapping("/login")
    public ResponseEntity<EntityModel<ResultResponse>> login(@Valid @RequestBody LoginRequest request){
        UUID userUuid = userService.login(request);
        return ResponseEntity.ok(EntityModel.of(new ResultResponse<>(API_SUCCESS_LOGIN_USER,userUuid),
                linkTo(methodOn(UserController.class).login(request)).withSelfRel()));
    }

}
