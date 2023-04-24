package com.twoleader.backend.domain.studyRoom.exception;

// import com.techeer.port.voilio.global.error.ErrorCode;
// import com.techeer.port.voilio.global.error.exception.BusinessException;

import com.twoleader.backend.global.error.ErrorCode;
import com.twoleader.backend.global.error.exception.BusinessException;

public class NotFoundStudyRoom extends BusinessException {
  public NotFoundStudyRoom() {
    super(ErrorCode.STUDY_ROOM_NOT_FOUND);
  }
}
