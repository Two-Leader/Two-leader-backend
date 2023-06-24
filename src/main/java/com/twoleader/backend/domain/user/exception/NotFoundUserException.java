package com.twoleader.backend.domain.user.exception;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_USER_NOT_FOUND;

import com.twoleader.backend.global.error.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
  public NotFoundUserException() {
    super(API_ERROR_USER_NOT_FOUND);
  }
}
