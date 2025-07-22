package com.heardio.api.radio.application.dto;

import com.heardio.api.global.asset.RadioStationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRadioStationRequestDto(
        @NotBlank(message = "방송 서비스 uuid는 필수값입니다.")
        @Size(max = 36, message = "stationUuid는 최대 36자까지 입력 가능합니다.")
        String stationUuid,
        @NotNull(message = "방송 서비스 종류는 필수값입니다.")
        RadioStationType radioStationType,
        @NotBlank(message = "스트리밍 url은 필수값입니다.")
        @Size(max = 300, message = "방송 스트리밍 url은 최대 300자까지 입력 가능합니다.")
        String streamUrl,
        @Size(max = 500, message = "description은 최대 500자까지 입력 가능합니다.")
        String description
) {
}
