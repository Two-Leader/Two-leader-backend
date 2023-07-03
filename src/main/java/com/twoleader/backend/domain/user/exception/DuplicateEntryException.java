package com.twoleader.backend.domain.user.exception;

import com.twoleader.backend.global.error.exception.BusinessException;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_USER_ALREADY_REGISTRATION;

public class DuplicateEntryException extends BusinessException {
    public DuplicateEntryException() {
        super(API_ERROR_USER_ALREADY_REGISTRATION);
    }
}
