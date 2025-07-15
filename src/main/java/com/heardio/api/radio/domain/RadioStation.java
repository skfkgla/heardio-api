package com.heardio.api.radio.domain;

import com.heardio.api.global.asset.RadioStationType;
import com.heardio.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "radio_station")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RadioStation extends BaseEntity {

    @Column(name = "station_uuid", length = 36, nullable = false, updatable = false)
    private String stationUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "radio_station_type", length = 50, nullable = false)
    private RadioStationType radioStationType;

    @Column(name = "stream_url", length = 300, nullable = false)
    private String streamUrl;

    @Column(name = "description", length = 500)
    private String description;


    @Builder
    RadioStation(String stationUuid,
                 RadioStationType radioStationType,
                 String streamUrl,
                 String description) {
        this.stationUuid       = stationUuid;
        this.radioStationType  = radioStationType;
        this.streamUrl         = streamUrl;
        this.description       = description;
    }
}