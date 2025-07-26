package com.heardio.api.global.error;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.heardio.api.global.error.exception.BusinessBaseException;

import lombok.extern.slf4j.Slf4j;

/**
 * 전역 예외 처리기
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 로직 예외 처리
     */
    @ExceptionHandler(BusinessBaseException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessBaseException e) {
        log.error("Business Exception: {}", e.getMessage(), e);
        ErrorCode errorCode = e.getErrorCode();
        return createErrorResponseEntity(errorCode);
    }

    /**
     * Bean Validation 실패 시 발생 (@Valid, @NotBlank, @Size 등)
     * 여러 필드의 검증 오류를 하나의 메시지로 합쳐서 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String customMessage = e.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));

        return createErrorResponseEntity(ErrorCode.INVALID_INPUT_VALUE, customMessage);
    }

    /**
     * JSON 파싱 실패 시 발생 (잘못된 JSON, 타입 변환 오류 등)
     * 입력값, 필드명을 포함한 범용적인 에러 메시지 제공
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String customMessage = "잘못된 데이터 형식입니다.";

        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) e.getCause();
            customMessage = String.format("'%s'는 %s 필드에 유효하지 않은 타입입니다.",
                ife.getValue(),
                ife.getPath().get(0).getFieldName());
        }

        return createErrorResponseEntity(ErrorCode.INVALID_INPUT_VALUE, customMessage);
    }

    /**
     * 기타 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unexpected Exception: {}", e.getMessage(), e);
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode, String message) {
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(ErrorResponse.of(errorCode, message));
    }
}
