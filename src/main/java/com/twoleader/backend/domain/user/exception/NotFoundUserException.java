package com.twoleader.backend.domain.user.exception;

import com.twoleader.backend.global.error.exception.BusinessException;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_EXISTED_USER;
import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_USER_NOT_FOUND;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException(){super(API_ERROR_USER_NOT_FOUND );}
}
