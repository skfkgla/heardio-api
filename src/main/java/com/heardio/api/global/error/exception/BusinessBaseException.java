package com.heardio.api.global.error.exception;

import com.heardio.api.global.error.ErrorCode;
import lombok.Getter;

/**
 * 비즈니스 로직에서 발생하는 예외의 최상위 클래스
 */
@Getter
public class BusinessBaseException extends RuntimeException {
    
    private final ErrorCode errorCode;
    
    public BusinessBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
