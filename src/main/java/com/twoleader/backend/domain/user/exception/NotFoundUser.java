package com.twoleader.backend.domain.user.exception;

//import com.techeer.port.voilio.global.error.ErrorCode;
//import com.techeer.port.voilio.global.error.exception.BusinessException;

import com.twoleader.backend.global.error.ErrorCode;
import com.twoleader.backend.global.error.exception.BusinessException;

public class NotFoundUser extends BusinessException {
  public NotFoundUser() {
    super(ErrorCode.USER_NOT_FOUND);
  }
}
