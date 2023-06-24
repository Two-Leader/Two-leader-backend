package com.twoleader.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {주체}_{이유} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global
  API_ERROR_INTERNAL_SERVER(500, "G001", "서버 오류"),
  API_ERROR_INPUT_INVALID_VALUE(409, "G002", "잘못된 입력"),

  // StudyRoom
  API_ERROR_STUDY_ROOM_NOT_FOUND(400, "S001", "스터디방 없음"),

  // RoomUser
  API_ERROR_ROOM_USER_NOT_FOUND(400, "RU001", "유저 없음"),

  // User
  API_ERROR_EXISTED_USER(400,"U001","이미 존재하는 유저"),
  API_ERROR_USER_NOT_FOUND(400,"U002","유저 없음"),
  ;
  private final int status;
  private final String code;
  private final String message;
}
