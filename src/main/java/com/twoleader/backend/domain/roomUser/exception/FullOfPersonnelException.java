package com.twoleader.backend.domain.roomUser.exception;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_ROOM_USER_FULL;

import com.twoleader.backend.global.error.exception.BusinessException;

public class FullOfPersonnelException extends BusinessException {
  public FullOfPersonnelException() {
    super(API_ERROR_ROOM_USER_FULL);
  }
}
