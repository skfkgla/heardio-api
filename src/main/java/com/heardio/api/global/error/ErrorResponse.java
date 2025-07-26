package com.heardio.api.global.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 에러 응답을 위한 객체
 */
@Schema(description = "에러 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    
    @Schema(description = "에러 메시지", example = ".")
    private String message;
    
    @Schema(description = "에러 코드", example = "E001")
    private String code;
    
    @Schema(description = "에러 발생 시간", example = "2025-07-26T16:30:00")
    private String timestamp;
    
    private ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    
    private ErrorResponse(ErrorCode errorCode, String customMessage) {
        this.message = customMessage;
        this.code = errorCode.getCode();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(errorCode, customMessage);
    }
}
