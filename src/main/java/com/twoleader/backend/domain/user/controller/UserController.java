package com.twoleader.backend.domain.user.controller;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.service.UserService;
import com.twoleader.backend.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.twoleader.backend.global.result.ResultCode.GET_USER_SUCCESS;
import static com.twoleader.backend.global.result.ResultCode.STUDYROOM_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<EntityModel<ResultResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(
                new ResultResponse<>(STUDYROOM_REGISTRATION_SUCCESS)
                ,linkTo(methodOn(UserController.class).createUser(request)).withSelfRel()
        ));
    }

    @GetMapping
    public ResponseEntity<EntityModel<ResultResponse<GetUserResponse>>> getUser(@NotBlank @RequestBody GetUserRequest request){
        GetUserResponse user = userService.getUser(request);
        return ResponseEntity.ok(EntityModel.of(
                new ResultResponse<>(GET_USER_SUCCESS,user)
                ,linkTo(methodOn(UserController.class).getUser(request)).withSelfRel()
        ));
    }
}
