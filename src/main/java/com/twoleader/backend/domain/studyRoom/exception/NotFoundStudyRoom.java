package com.twoleader.backend.domain.studyRoom.exception;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_STUDY_ROOM_NOT_FOUND;

import com.twoleader.backend.global.error.exception.BusinessException;

public class NotFoundStudyRoom extends BusinessException {
  public NotFoundStudyRoom() {
    super(API_ERROR_STUDY_ROOM_NOT_FOUND);
  }
}
