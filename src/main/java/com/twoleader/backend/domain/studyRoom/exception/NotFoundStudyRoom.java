package com.twoleader.backend.domain.studyRoom.exception;

import com.twoleader.backend.global.error.exception.BusinessException;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_STUDY_ROOM_NOT_FOUND;

public class NotFoundStudyRoom extends BusinessException {
  public NotFoundStudyRoom() {
    super(API_ERROR_STUDY_ROOM_NOT_FOUND);
  }
}
