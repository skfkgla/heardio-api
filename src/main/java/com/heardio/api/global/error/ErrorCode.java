package com.heardio.api.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// ─────────── 일반적인 에러 ───────────
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E001", "올바르지 않은 입력값입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E003", "서버 내부 오류가 발생했습니다."),

	// ─────────── 라디오 방송국 관련 에러 ───────────
	RADIO_STATION_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "라디오 방송국을 찾을 수 없습니다.");

	// 향후 추가될 수 있는 다른 도메인 에러들은 여기에 추가

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
