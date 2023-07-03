package com.twoleader.backend.domain.roomUser.exception;

import com.twoleader.backend.global.error.exception.BusinessException;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_ROOM_USER_ALREADY_REGISTRATION;

public class DuplicateEntryException extends BusinessException {
    public DuplicateEntryException() {
        super(API_ERROR_ROOM_USER_ALREADY_REGISTRATION);
    }
}
