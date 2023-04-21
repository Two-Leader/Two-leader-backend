package com.twoleader.backend.domain.studyRoom.controller;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.dto.response.FindStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import com.twoleader.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.twoleader.backend.global.result.ResultCode.GET_ALL_STUDYROOM;
import static com.twoleader.backend.global.result.ResultCode.STUDYROOM_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/api/v1/study")
@RestController
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    @Operation(summary = "Study Room 생성 요청", description = "Study Room을 생성합니다.", tags = { "StudyRoom Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED(성공)"),
            @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
    })
    @PostMapping("")
    public ResponseEntity<EntityModel<ResultResponse>> createStudyRoom(@Valid @RequestBody CreateStudyRoomDto studyRoomDto) {
        studyRoomService.createStudyRoom(studyRoomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(new ResultResponse<>(STUDYROOM_REGISTRATION_SUCCESS),linkTo(methodOn(StudyRoomController.class).createStudyRoom(studyRoomDto)).withSelfRel()));
    }

    @Operation(summary = "Study Room 모두 조회 요청", description = "모든 Study Room을 조회합니다.",tags = {"StudyRoom Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK(성공), 스터디가 없을 경우 비어있을 수 있습니다."),
            @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
    })
    @GetMapping("")
    public ResponseEntity<EntityModel<ResultResponse<List<FindStudyRoomDto>>>> getAllStudyRoom(){
        List<FindStudyRoomDto> studyRooms = studyRoomService.findAllStudyRoom();
        return ResponseEntity.ok(EntityModel.of(new ResultResponse<>(GET_ALL_STUDYROOM,studyRooms),linkTo(methodOn(StudyRoomController.class).getAllStudyRoom()).withSelfRel()));
    }
}
