package com.twoleader.backend.global.result.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

  // 도메인 별로 나눠서 관리(ex: StudyRoom 도메인)
  // studyRoom
  API_SUCCESS_STUDYROOM_REGISTRATION("S001", "스터디방이 정상적으로 등록되었습니다."),
  API_SUCCESS_STUDYROOM_GET_ALL("S002", "모든 스터디방을 정상적으로 불러왔습니다."),
  API_SUCCESS_STUDYROOM_GET("S003", "스터디방을 정상적으로 불러왔습니다."),

  // user
  API_SUCCESS_USER_GET("U001", "유저를 정상적으로 불러왔습니다."),
  API_SUCCESS_USER_REGISTRATION("U002", "유저가 정상적으로 등록되었습니다."),
  API_SUCCESS_USER_DELETE("U003", "유저를 정삭적으로 삭제했습니다."),
  ;

  private final String code;
  private final String message;
}
