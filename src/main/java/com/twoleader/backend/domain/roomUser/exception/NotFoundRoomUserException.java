package com.twoleader.backend.domain.roomUser.exception;

import com.twoleader.backend.global.error.ErrorCode;
import com.twoleader.backend.global.error.exception.BusinessException;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_ROOM_USER_NOT_FOUND;

public class NotFoundRoomUserException extends BusinessException {
  public NotFoundRoomUserException() {
    super(API_ERROR_ROOM_USER_NOT_FOUND);
  }
}
