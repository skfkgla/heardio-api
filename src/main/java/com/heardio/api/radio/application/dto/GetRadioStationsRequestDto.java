package com.heardio.api.radio.application.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GetRadioStationsRequestDto(
        int page,
        int pageSize,
        List<String> sort
) {
}
