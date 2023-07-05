package com.twoleader.backend.domain.studyRoom.controller;

import static com.twoleader.backend.global.result.api.ResultCode.*;

import com.twoleader.backend.domain.studyRoom.dto.request.CheckStudyRoomPasswordRequest;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetAllStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/studies")
@RestController
public class StudyRoomController {

  private final StudyRoomService studyRoomService;
  private final StudyRoomMapper studyRoomMapper;

  @Operation(summary = "Study Room 생성 요청", description = "Study Room을 생성합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "CREATED(성공)"),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @PostMapping("")
  public ResponseEntity createStudyRoom(@Valid @RequestBody CreateStudyRoomRequest request) {
    studyRoomService.createStudyRoom(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(studyRoomMapper.toModel(API_SUCCESS_STUDY_ROOM_REGISTRATION, null, request));
  }

  @Operation(summary = "Study Room 모두 조회 요청", description = "모든 Study Room을 조회합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK(성공), 스터디가 없을 경우 비어있을 수 있습니다."),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @GetMapping("")
  public ResponseEntity getAllStudyRoom(
      @PageableDefault(size = 10, sort = "createAt") Pageable pageable) {
    Page<GetAllStudyRoomResponse> responsePage = studyRoomService.findAllStudyRoom(pageable);
    return ResponseEntity.ok(
        studyRoomMapper.toModel(API_SUCCESS_STUDY_ROOM_GET_ALL, responsePage, pageable));
  }

  @Operation(summary = "Study Room 개별 조회", description = "StudyRoom을 uuid로 개별 조회합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK(성공)"),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @GetMapping("/{roomUuid}")
  public ResponseEntity getStudyRoomByUuid(@PathVariable("roomUuid") UUID roomUuid) {
    GetStudyRoomResponse response = studyRoomService.findStudyRoomByUuid(roomUuid);
    return ResponseEntity.ok(
        studyRoomMapper.toModel(API_SUCCESS_STUDY_ROOM_GET, response, roomUuid));
  }

  @Operation(summary = "Study Room 비밀번호 확인", description = "StudyRoom의 비밀번호를 확인합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK(성공)"),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @GetMapping("/{roomUuid}/checkpw")
  public ResponseEntity checkStudyRoomPassword(
      @PathVariable("roomUuid") UUID roomUuid,
      @Valid @RequestBody CheckStudyRoomPasswordRequest request) {
    boolean response = studyRoomService.checkStudyRoomPassword(roomUuid, request.getPassword());
    return ResponseEntity.ok(
        studyRoomMapper.toModel(
            API_SUCCESS_STUDY_ROOM_CHECK_PASSWORD, response, roomUuid, request));
  }

  @Operation(summary = "Study Room 삭제", description = "StudyRoom을 삭제합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK(성공)"),
    @ApiResponse(responseCode = "409", description = "INPUT_INVALID_VALUE(잘못된 입력)"),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR(서버 오류)"),
  })
  @DeleteMapping("/{roomUuid}")
  public ResponseEntity deleteStudyRoom(@PathVariable("roomUuid") UUID roomUuid) {
    studyRoomService.deleteStudyRoom(roomUuid);
    return ResponseEntity.ok(
        studyRoomMapper.toModel(API_SUCCESS_STUDY_ROOM_DELETE, null, roomUuid));
  }
}
