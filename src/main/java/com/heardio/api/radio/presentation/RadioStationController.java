package com.heardio.api.radio.presentation;

import com.heardio.api.radio.application.RadioStationService;
import com.heardio.api.radio.application.dto.CreateRadioStationRequestDto;
import com.heardio.api.radio.application.dto.GetRadioStationsRequestDto;
import com.heardio.api.radio.application.dto.GetRadioStationsResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "라디오 방송국")
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/v1/radio/station")
@RestController
public class RadioStationController {
    private final RadioStationService radioStationService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRadioStation(@RequestBody CreateRadioStationRequestDto request) {
        radioStationService.createRadioStation(request);
    }

    @GetMapping("")
    public GetRadioStationsResponseDto getRadioStations(@RequestParam("page") int page,
                                                        @RequestParam("pageSize") int pageSize,
                                                        @RequestParam(value = "sort", defaultValue = "clickCount,desc") List<String> sort) {
        GetRadioStationsRequestDto request = GetRadioStationsRequestDto.builder()
                .page(page)
                .pageSize(pageSize)
                .sort(sort)
                .build();
        return radioStationService.getRadioStations(request);
    }
}
