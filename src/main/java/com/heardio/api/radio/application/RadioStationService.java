package com.heardio.api.radio.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heardio.api.global.error.ErrorCode;
import com.heardio.api.global.error.exception.NotFoundException;
import com.heardio.api.radio.application.dto.CreateRadioStationRequestDto;
import com.heardio.api.radio.application.dto.GetRadioStationResponseDto;
import com.heardio.api.radio.application.dto.GetRadioStationsRequestDto;
import com.heardio.api.radio.application.dto.GetRadioStationsResponseDto;
import com.heardio.api.radio.application.dto.UpdateRadioStationRequestDto;
import com.heardio.api.radio.domain.RadioStation;
import com.heardio.api.radio.domain.repository.RadioStationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
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

	@Transactional(readOnly = true)
	public GetRadioStationsResponseDto getRadioStations(GetRadioStationsRequestDto request) {
		PageRequest pageRequest = PageRequest.of(request.page(), request.pageSize(),
			Sort.by(Sort.Direction.fromString(request.sort().get(1)), request.sort().get(0)));
		Page<RadioStation> result = radioStationRepository.findAll(pageRequest);
		return GetRadioStationsResponseDto.from(result);
	}

	public GetRadioStationResponseDto getRadioStation(Long radioStationId) {
		RadioStation radioStation = radioStationRepository.findById(radioStationId)
			.orElseThrow(() -> new NotFoundException(ErrorCode.RADIO_STATION_NOT_FOUND));
		radioStation.increaseClickCount();
		return GetRadioStationResponseDto.from(radioStation);
	}

	public void updateRadioStation(Long radioStationId, UpdateRadioStationRequestDto request) {
		RadioStation radioStation = radioStationRepository.findById(radioStationId)
			.orElseThrow(() -> new NotFoundException(ErrorCode.RADIO_STATION_NOT_FOUND));
		radioStation.updateRadioStation(request.stationUuid(), request.radioStationType(), request.streamUrl(),
			request.description());
	}

	public void deleteRadioStation(Long radioStationId) {
		RadioStation radioStation = radioStationRepository.findById(radioStationId)
			.orElseThrow(() -> new NotFoundException(ErrorCode.RADIO_STATION_NOT_FOUND));
		radioStationRepository.delete(radioStation);
	}
}
