package com.heardio.api.radio.domain;

import com.heardio.api.global.asset.RadioStationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "radio_station")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RadioStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 36)
	@Column(name = "station_uuid", length = 36, nullable = false)
	private String stationUuid;

	@NotNull
	@Size(max = 50)
	@Enumerated(EnumType.STRING)
	@Column(name = "radio_station_type", length = 50, nullable = false)
	private RadioStationType radioStationType;

	@NotNull
	@Size(max = 300)
	@Column(name = "stream_url", length = 300, nullable = false)
	private String streamUrl;

	@Size(max = 500)
	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "click_count")
	private long clickCount;

	@Builder
	RadioStation(Long id, String stationUuid, RadioStationType radioStationType, String streamUrl, String description,
		long clickCount) {
		this.stationUuid = stationUuid;
		this.radioStationType = radioStationType;
		this.streamUrl = streamUrl;
		this.description = description;
		this.clickCount = clickCount;
	}

	public void increaseClickCount() {
		this.clickCount++;
	}

	/**
	 * 방송국 정보를 업데이트합니다.
	 * 도메인 규칙을 검증하고 위반 시 예외를 던집니다.
	 */
	public void updateRadioStation(String stationUuid, RadioStationType radioStationType, String streamUrl,
		String description) {
		this.stationUuid = stationUuid;
		this.radioStationType = radioStationType;
		this.streamUrl = streamUrl;
		this.description = description;
	}
}