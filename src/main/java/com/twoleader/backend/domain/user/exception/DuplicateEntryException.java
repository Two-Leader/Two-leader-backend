package com.twoleader.backend.domain.user.exception;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_USER_ALREADY_REGISTRATION;

import com.twoleader.backend.global.error.exception.BusinessException;

public class DuplicateEntryException extends BusinessException {
  public DuplicateEntryException() {
    super(API_ERROR_USER_ALREADY_REGISTRATION);
  }
}
