package com.twoleader.backend.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

  // 도메인 별로 나눠서 관리(ex: StudyRoom 도메인)
  // studyRoom
  STUDYROOM_REGISTRATION_SUCCESS("S001", "스터디방이 정상적으로 등록되었습니다."),
  GET_ALL_STUDYROOM_SUCCESS("S002", "모든 스터디방을 정상적으로 불러왔습니다."),
  GET_STUDYROOM_SUCCESS("S003", "스터디방을 정상적으로 불러왔습니다."),

  // user
  GET_USER_SUCCESS("U001", "유저를 정상적으로 불러왔습니다."),
  USER_REGISTRATION_SUCCESS("U002", "유저가 정상적으로 등록되었습니다."),
  ;

  private final String code;
  private final String message;
}
