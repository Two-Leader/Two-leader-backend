package com.twoleader.backend.domain.studyRoom.controller;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/study")
@RestController
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @Operation(summary = "Study Room 생성 요청", description = "Study Room을 생성합니다.", tags = { "StudyRoom Controller" })
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(schema = @Schema(implementation = DeleteMemberResponse.class))),
////            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
////            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
////            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//    })
    @PostMapping("")
    public void createStudyRoom( @RequestBody CreateStudyRoomDto studyRoomDto) {
        StudyRoom studyRoom = studyRoomService.createStudyRoom(studyRoomDto);
        log.info("CREATE Chat Room [{}]", studyRoom.toString());
    }

    @Operation(summary = "Study Room 모두 조회 요청", description = "모든 Study Room을 조회합니다.",tags = {"StudyRoom Controller"})
    @GetMapping("")
    public List<StudyRoom> getAllStudyRoom(){
        List<StudyRoom> studyRooms = studyRoomService.findAllStudyRoom();
        log.info("getAllStudyRoom [{}]",studyRooms.toString());
        return studyRooms;
    }
}
