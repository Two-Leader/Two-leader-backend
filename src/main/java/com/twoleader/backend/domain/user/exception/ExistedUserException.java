package com.twoleader.backend.domain.user.exception;

import com.twoleader.backend.global.error.exception.BusinessException;

import static com.twoleader.backend.global.error.ErrorCode.API_ERROR_EXISTED_USER;

public class ExistedUserException extends BusinessException{
    public ExistedUserException(){super(API_ERROR_EXISTED_USER);}
}