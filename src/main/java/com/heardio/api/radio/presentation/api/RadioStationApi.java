package com.heardio.api.radio.presentation.api;

import java.util.List;

import com.heardio.api.global.error.ErrorResponse;
import com.heardio.api.radio.application.dto.CreateRadioStationRequestDto;
import com.heardio.api.radio.application.dto.GetRadioStationResponseDto;
import com.heardio.api.radio.application.dto.GetRadioStationsResponseDto;
import com.heardio.api.radio.application.dto.UpdateRadioStationRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 라디오 방송국 API 명세 (스웨거 문서화만)
 */
@Tag(name = "라디오 방송국", description = "라디오 방송국 관련 API")
public interface RadioStationApi {

	@Operation(summary = "라디오 방송국 생성(어드민 전용)", description = "새로운 라디오 방송국을 생성합니다.(어드민 전용)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "라디오 방송국 생성 성공"),
		@ApiResponse(responseCode = "403", description = "권한 없음 (어드민 전용)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	void createRadioStation(
		@Parameter(description = "라디오 방송국 생성 요청 데이터", required = true)
		CreateRadioStationRequestDto request
	);

	@Operation(summary = "라디오 방송국 목록 조회", description = "페이징된 라디오 방송국 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "라디오 방송국 목록 조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	GetRadioStationsResponseDto getRadioStations(
		@Parameter(description = "페이지 번호 (0부터 시작)", required = true, example = "0")
		int page,

		@Parameter(description = "페이지 크기", required = true, example = "10")
		int pageSize,

		@Parameter(description = "정렬 기준 (필드명,정렬방향)", example = "[\"clickCount\", \"desc\"]")
		List<String> sort
	);

	@Operation(summary = "라디오 방송국 상세 조회", description = "ID로 특정 라디오 방송국의 상세 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "라디오 방송국 상세 정보 조회 성공"),
		@ApiResponse(responseCode = "404", description = "라디오 방송국을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	GetRadioStationResponseDto getRadioStation(
		@Parameter(description = "라디오 방송국 ID", required = true, example = "1") Long radioStationId);

	@Operation(summary = "라디오 방송국 수정(어드민 전용)", description = "ID로 특정 라디오 방송국의 정보를 수정합니다.(어드민 전용)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "라디오 방송국 수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", description = "권한 없음 (어드민 전용)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", description = "라디오 방송국을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	void updateRadioStation(@Parameter(description = "라디오 방송국 ID", required = true, example = "1") Long radioStationId,
		@Parameter(description = "라디오 방송국 수정 요청 데이터", required = true) UpdateRadioStationRequestDto request
	);

	@Operation(summary = "라디오 방송국 삭제(어드민 전용)", description = "ID로 특정 라디오 방송국의 정보를 삭제합니다.(어드민 전용)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "라디오 방송국 삭제 성공"),
		@ApiResponse(responseCode = "403", description = "권한 없음 (어드민 전용)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", description = "라디오 방송국을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	void deleteRadioStation(@Parameter(description = "라디오 방송국 ID", required = true, example = "1") Long radioStationId);
}
