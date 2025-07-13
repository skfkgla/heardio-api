package com.heardio.api.radio.domain;

import com.heardio.api.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RadioStation extends BaseEntity {
    @Column(name = "station_uuid", length = 36, nullable = false)
    private String stationUuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "stream_url", length = 300, nullable = false)
    private String streamUrl;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "change_uuid", length = 36)
    private String changeUuid;
}
