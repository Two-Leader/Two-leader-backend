package com.twoleader.backend.domain.roomUser.aspect;

import com.twoleader.backend.domain.roomUser.exception.DuplicateEntryException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoomUserAspect {
    @AfterThrowing(pointcut = "execution(* com.twoleader.backend.domain.roomUser.service.RoomUserService.createUser(..))",throwing = "exception")
    public void test(DataIntegrityViolationException exception){
        throw new DuplicateEntryException();
    }
}
