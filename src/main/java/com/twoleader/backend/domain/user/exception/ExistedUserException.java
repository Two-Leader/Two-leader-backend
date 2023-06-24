package com.twoleader.backend.domain.user.exception;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_EXISTED_USER;

import com.twoleader.backend.global.error.exception.BusinessException;

public class ExistedUserException extends BusinessException {
  public ExistedUserException() {
    super(API_ERROR_EXISTED_USER);
  }
}
