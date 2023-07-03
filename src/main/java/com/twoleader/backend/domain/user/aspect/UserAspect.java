package com.twoleader.backend.domain.user.aspect;

import com.twoleader.backend.domain.user.exception.DuplicateEntryException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserAspect {

    @AfterThrowing(pointcut = "execution(* com.twoleader.backend.domain.user.service.UserService.signup(..))",throwing = "exception")
    public void handleDuplicateEntryException(DataIntegrityViolationException exception){
            throw new DuplicateEntryException();
    }
}
