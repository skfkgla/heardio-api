package com.heardio.api.radio.application.dto;

import com.heardio.api.global.asset.RadioStationType;
import com.heardio.api.radio.domain.RadioStation;

public record GetRadioStationResponseDto(
        Long radioStationId,
        RadioStationType radioStationType,
        String streamUrl,
        String description
) {
    public static GetRadioStationResponseDto from(RadioStation radioStation) {
        return new GetRadioStationResponseDto(radioStation.getId(),
                radioStation.getRadioStationType(),
                radioStation.getStreamUrl(),
                radioStation.getDescription());
    }
}
