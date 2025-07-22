package com.heardio.api.radio.application;

import com.heardio.api.radio.application.dto.CreateRadioStationRequestDto;
import com.heardio.api.radio.domain.RadioStation;
import com.heardio.api.radio.domain.repository.RadioStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RadioStationService {
    private final RadioStationRepository radioStationRepository;

    public void createRadioStation(CreateRadioStationRequestDto request) {
        RadioStation radioStation = RadioStation.builder()
                .stationUuid(request.stationUuid())
                .radioStationType(request.radioStationType())
                .streamUrl(request.streamUrl())
                .description(request.description())
                .build();
        radioStationRepository.save(radioStation);
    }
}
