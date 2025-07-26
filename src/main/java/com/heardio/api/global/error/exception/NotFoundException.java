package com.heardio.api.global.error.exception;

import com.heardio.api.global.error.ErrorCode;

/**
 * 엔티티를 찾을 수 없을 때 발생하는 예외
 */
public class NotFoundException extends BusinessBaseException {
    
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
