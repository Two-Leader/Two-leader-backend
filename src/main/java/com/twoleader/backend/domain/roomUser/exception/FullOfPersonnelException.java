package com.twoleader.backend.domain.roomUser.exception;

import com.twoleader.backend.global.error.exception.BusinessException;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_ROOM_USER_FULL;
import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_ROOM_USER_NOT_FOUND;

public class FullOfPersonnelException extends BusinessException {
    public FullOfPersonnelException() {
        super(API_ERROR_ROOM_USER_FULL);
    }
}
