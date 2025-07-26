package com.heardio.api.radio.presentation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.heardio.api.radio.application.RadioStationService;
import com.heardio.api.radio.application.dto.CreateRadioStationRequestDto;
import com.heardio.api.radio.application.dto.GetRadioStationResponseDto;
import com.heardio.api.radio.application.dto.GetRadioStationsRequestDto;
import com.heardio.api.radio.application.dto.GetRadioStationsResponseDto;
import com.heardio.api.radio.presentation.api.RadioStationApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/radio-stations")
public class RadioStationController implements RadioStationApi {

	private final RadioStationService radioStationService;

	@Override
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void createRadioStation(@RequestBody @Validated CreateRadioStationRequestDto request) {
		radioStationService.createRadioStation(request);
	}

	@Override
	@GetMapping("")
	public GetRadioStationsResponseDto getRadioStations(
		@RequestParam("page") int page,
		@RequestParam("pageSize") int pageSize,
		@RequestParam(value = "sort", defaultValue = "clickCount,desc") List<String> sort) {
		GetRadioStationsRequestDto request = GetRadioStationsRequestDto.builder()
			.page(page)
			.pageSize(pageSize)
			.sort(sort)
			.build();
		return radioStationService.getRadioStations(request);
	}

	@Override
	@GetMapping("/{radioStationId}")
	public GetRadioStationResponseDto getRadioStation(@PathVariable("radioStationId") Long radioStationId) {
		return radioStationService.getRadioStation(radioStationId);
	}
}
