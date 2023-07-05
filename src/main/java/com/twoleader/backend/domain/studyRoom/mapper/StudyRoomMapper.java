package com.twoleader.backend.domain.studyRoom.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.studyRoom.controller.StudyRoomController;
import com.twoleader.backend.domain.studyRoom.dto.request.CheckStudyRoomPasswordRequest;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetAllStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.global.result.api.ResultCode;
import com.twoleader.backend.global.result.api.ResultResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class StudyRoomMapper {

  public StudyRoom toEntity(CreateStudyRoomRequest request, User user) {
    return StudyRoom.builder()
        .roomName(request.getRoomName())
        .information(request.getInformation())
        .password(request.getPassword())
        .totalNop(request.getTotalNop())
        .constructor(user)
        .build();
  }

  public Page<GetAllStudyRoomResponse> toDto(Page<StudyRoom> studyRooms) {
    return studyRooms.map(this::toDto);
  }

  public GetAllStudyRoomResponse toDto(StudyRoom studyRoom) {
    return GetAllStudyRoomResponse.builder()
        .roomUuid(studyRoom.getRoomUuid())
        .roomName(studyRoom.getRoomName())
        .roomInformation(studyRoom.getInformation())
        .totalNop(studyRoom.getTotalNop())
        .nowTotalNop(studyRoom.getNowTotalNop())
        .isLocked(studyRoom.getPassword() != null)
        .constructorName(studyRoom.getConstructor().getNickName())
        .build();
  }

  public GetStudyRoomResponse toDto(StudyRoom studyRoom, List<RoomUser> users) {
    return GetStudyRoomResponse.builder()
        .roomName(studyRoom.getRoomName())
        .constructorName(studyRoom.getConstructor().getNickName())
        .users(users.stream().map(this::toDto).collect(Collectors.toList()))
        .build();
  }

  private GetRoomUserResponse toDto(RoomUser user) {
    return GetRoomUserResponse.builder()
        .userId(user.getRoomUserId())
        .userName(user.getRoomUserName())
        .userUuid(user.getUser().getUserUuid())
        .build();
  }

  public EntityModel toModel(ResultCode code, Object response, Object... request) {
    switch (code) {
      case API_SUCCESS_STUDY_ROOM_GET:
        return EntityModel.of(
            new ResultResponse<>(code, response),
            linkTo(methodOn(StudyRoomController.class).getStudyRoomByUuid((UUID) request[0]))
                .withSelfRel());
      case API_SUCCESS_STUDY_ROOM_REGISTRATION:
        return EntityModel.of(
            new ResultResponse<>(code),
            linkTo(
                    methodOn(StudyRoomController.class)
                        .createStudyRoom((CreateStudyRoomRequest) request[0]))
                .withSelfRel());
      case API_SUCCESS_STUDY_ROOM_DELETE:
        return EntityModel.of(
            new ResultResponse<>(code),
            linkTo(methodOn(StudyRoomController.class).deleteStudyRoom((UUID) request[0]))
                .withSelfRel());
      case API_SUCCESS_STUDY_ROOM_CHECK_PASSWORD:
        return EntityModel.of(
            new ResultResponse<>(code, response),
            linkTo(
                    methodOn(StudyRoomController.class)
                        .checkStudyRoomPassword(
                            (UUID) request[0], (CheckStudyRoomPasswordRequest) request[1]))
                .withSelfRel());
    }
    return EntityModel.of(new ResultResponse<>(code));
  }

  public EntityModel toModel(
      ResultCode code, Page<GetAllStudyRoomResponse> responses, int page, int size) {
    List<EntityModel<GetAllStudyRoomResponse>> response =
        responses
            .get()
            .map(
                studyRoom ->
                    EntityModel.of(
                        studyRoom,
                        linkTo(
                                methodOn(StudyRoomController.class)
                                    .getStudyRoomByUuid(studyRoom.getRoomUuid()))
                            .withSelfRel()))
            .collect(Collectors.toList());

    EntityModel<ResultResponse<Object>> entity =  EntityModel.of(
        new ResultResponse<>(code, response),
        linkTo(methodOn(StudyRoomController.class).getAllStudyRoom(page,size)).withSelfRel());
    if(responses.hasNext()) entity.add(linkTo(methodOn(StudyRoomController.class).getAllStudyRoom(page+1,size)).withRel("next"));

   return entity;
  }
}
