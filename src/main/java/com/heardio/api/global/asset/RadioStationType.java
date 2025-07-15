package com.heardio.api.global.asset;

import lombok.Getter;

@Getter
public enum RadioStationType {
    // ─────────── 지상파 ───────────
    KBS_RADIO_1("KBS 제1라디오", "뉴스, 시사, 교양"),
    KBS_HAPPY_FM("KBS 해피FM", "제2라디오 (중장년층 대상 대중음악)"),
    KBS_RADIO_3("KBS 제3라디오", "장애인·다문화 가정을 위한 사랑의 소리방송"),
    KBS_CLASSIC_FM("KBS 클래식FM", "제1FM (서양 고전 음악 및 한국 국악)"),
    KBS_COOL_FM("KBS 쿨FM", "제2FM (젊은층 대상 대중음악)"),
    KBS_HANMINJOK_BROADCAST("KBS 한민족방송", "남북화합과 교류를 위한 한민족 네트워크 채널"),
    EBS_FM("EBS FM", "책 읽어주는 라디오"),
    MBC_STANDARD_FM("MBC 표준FM", "종합 방송 : 뉴스, 시사, 교양, 대중음악"),
    MBC_FM4U("MBC FM4U", "대중음악"),
    SBS_LOVE_FM("SBS 러브FM", "종합 방송 : 뉴스, 시사, 교양, 대중음악"),
    SBS_POWER_FM("SBS 파워FM", "대중음악"),

    // ─────────── 지역민방 ───────────
    GYEONGIN_BANGSONG("경인방송", "수도권 종합 방송 : 뉴스, 시사, 교양, 음악"),
    OBS_RADIO("OBS 라디오", "수도권 종합 방송 : 뉴스, 시사, 교양, 음악"),
    G1_FRESH_FM("G1 Fresh FM", "강원권 종합 방송 : 음악"),
    TJB_POWER_FM("TJB POWER FM", "대전·세종·충남권 종합 방송 : 음악"),
    CJB_JOY_FM("CJB JOY FM", "충북권 종합 방송 : 음악"),
    JTV_MAGIC_FM("JTV MAGIC FM", "전북권 종합 방송 : 음악"),
    KBC_MY_FM("kbc My FM", "광주·전남권 종합 방송 : 뉴스, 음악"),
    TBC_DREAM_FM("TBC Dream FM", "대구·경북권 종합 방송 : 뉴스, 음악"),
    KNN_LOVE_FM("KNN 러브FM", "부산·경남권 종합 방송 : 뉴스, 시사, 교양, 음악"),
    KNN_POWER_FM("KNN 파워FM", "부산·경남권 대중음악"),
    UBC_GREEN_FM("ubc Green FM", "울산권 종합 방송 : 뉴스, 음악"),
    JIBS_NEW_POWER_FM("JIBS New Power FM", "제주권 종합 방송 : 음악"),

    // ─────────── 종교 ───────────
    CBS_STANDARD_FM("CBS 표준FM", "기독교 종합 방송 : 뉴스, 시사, 교양, 음악, 선교"),
    CBS_MUSIC_FM("CBS 음악FM", "아름다운 음악이 흐르는 라디오 : 대중, 고전, CCM"),
    FEBC_FM("FEBC FM", "아름다운 찬양과 구원의 기쁜 소식을 전하는 순수 기독교 복음 방송"),
    CPBC_FM("Cpbc FM", "생명 사랑을 모토로 기쁜 소식 밝은 세상을 만들어가는 천주교 종합 방송"),
    BBS_FM("BBS FM", "깨침의 소리와 나누는 기쁨으로 향기로운 세상을 만들어가는 불교 종합 방송"),
    WBS_FM("WBS FM", "맑고 밝고 훈훈한 소리를 통해 열린 방송을 지향하는 원불교 종합 방송"),

    // ─────────── 전문 ───────────
    TBS_FM("TBS FM", "수도권 교통·생활정보 채널"),
    TBN_TRAFFIC("TBN 교통방송", "전국 대상 신속 정확한 교통정보 채널"),
    YTN_RADIO("YTN라디오", "24시간 뉴스 채널"),
    TBS_EFM("TBS eFM", "수도권 거주 외국인을 위한 외국어 방송"),
    BEFM("BeFM", "부산·경남 거주 외국인을 위한 외국어 방송"),
    GGN("GGN", "광주·전남 거주 외국인을 위한 외국어 방송"),
    ARIRANG_RADIO("아리랑 라디오", "제주도 거주 외국인을 위한 외국어 방송"),
    GUGAK_BROADCAST("국악방송", "국악 전문 채널"),
    KFN_RADIO("KFN 라디오", "국방홍보 전문 채널"),
    AFN_THUNDER_AM("AFN Thunder AM", "미군방송"),
    AFN_EAGLE_FM("AFN Eagle FM", "미군방송"),

    // ─────────── 지상파 DMB ───────────
    SBS_V_RADIO("SBS V-Radio", "자체제작 프로그램을 재전송하는 채널"),
    U_KBS_MUSIC("U-KBS MUSIC", "쿨FM(2FM) 및 자체제작 프로그램 재전송 채널"),
    ALL_THAT_MUSIC("올댓뮤직", "자체제작 프로그램을 재전송하는 채널"),

    // ─────────── 지역공동체 ───────────
    GWANAK_FM("관악FM", "서울 관악구 지역 FM 방송"),
    MAPO_FM("마포FM", "서울 마포구 지역 FM 방송"),
    SEONGNAM_FM("성남FM", "경기 성남시 분당구 지역 FM 방송"),
    GEUMGANG_FM("금강FM", "충남 공주 지역 FM 방송"),
    SEONGSEO_COMMUNITY_FM("성서공동체FM", "대구 달서구 지역 FM 방송"),
    GWANGJU_NORTH_FM("광주시민방송 북구FM", "광주 북구 지역 FM 방송"),
    YEONGJU_FM("영주FM", "경북 영주시 지역 FM 방송"),
    GBS_GORYEO_FM("GBS 고려방송FM", "광주 광산구 지역 FM 방송"),
    OKCHEON_FM("옥천FM", "충북 옥천군 지역 FM 방송"),
    SEONGJU_FM("성주FM", "경북 성주군 지역 FM 방송"),
    SEJONG_FM("세종FM", "세종특별자치시 지역 FM 방송"),
    DAEJEON_CULTURE_FM("대전생활문화방송", "대전 서구 지역 FM 방송"),
    NAMHAE_COMMUNITY_RADIO("남해FM공동체라디오방송", "경남 남해군 지역 FM 방송"),
    SEODAEMUN_VILLAGE_RADIO("서대문마을공동체라디오", "서울 서대문구 지역 FM 방송"),
    SUWON_FM("수원FM", "경기 수원시 지역 FM 방송"),
    YEONGWOL_COMMUNITY_RADIO("영월FM공동체라디오", "강원 영월군 지역 FM 방송"),
    YEONJE_COMMUNITY_RADIO("연제FM공동체라디오", "부산 연제구 지역 FM 방송"),
    JEONJU_COMMUNITY_RADIO("전주공동체라디오", "전북 전주시 지역 FM 방송"),
    DANWON_FM("단원FM", "경기 안산시 지역 FM 방송"),

    // ─────────── 인터넷 라디오 ───────────
    EBS_BANDI("EBS 반디", "외국어 전문채널"),
    CBS_JOY4U("CBS JOY4U", "크리스천 뮤직 전문채널"),
    BTN_RADIO("BTN라디오", "불교 라디오 전문채널"),
    CTS_RADIO("CTS라디오", "크리스천 라디오 전문채널");

    private final String displayName;
    private final String description;

    RadioStationType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    @Override
    public String toString() {
        return displayName + " (" + description + ")";
    }
}
