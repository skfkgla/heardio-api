package com.heardio.api.radio.application.dto;

import com.heardio.api.radio.domain.RadioStation;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetRadioStationsResponseDto(
        List<GetRadioStationResponseDto> list,
        MetadataDto metadataDto
) {
    public record MetadataDto(long totalElements) {
    }

    public static GetRadioStationsResponseDto from(Page<RadioStation> page) {
        List<GetRadioStationResponseDto> list = page.getContent().stream()
                .map(GetRadioStationResponseDto::from)
                .toList();

        MetadataDto metadataDto = new MetadataDto(page.getTotalElements());

        return new GetRadioStationsResponseDto(list, metadataDto);
    }
}
