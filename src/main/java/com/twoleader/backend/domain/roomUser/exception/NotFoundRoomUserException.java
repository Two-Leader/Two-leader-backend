package com.twoleader.backend.domain.roomUser.exception;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_ROOM_USER_NOT_FOUND;

import com.twoleader.backend.global.error.exception.BusinessException;

public class NotFoundRoomUserException extends BusinessException {
  public NotFoundRoomUserException() {
    super(API_ERROR_ROOM_USER_NOT_FOUND);
  }
}
