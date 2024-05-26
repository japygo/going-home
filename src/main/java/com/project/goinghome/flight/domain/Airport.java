package com.project.goinghome.flight.domain;

public enum Airport {
    SEL(null, "서울"),
    CJU("제주국제공항", "제주"),
    GMP("김포국제공항", "김포"),
    PUS("김해국제공항", "부산"),
    CJJ("청주국제공항", "청주"),
    TAE("대구국제공항", "대구"),
    KWJ("광주공항", "광주"),
    RSU("여수공항", "여수"),
    USN("울산공항", "울산"),
    HIN("사천공항", "사천"),
    KPO("포항경주공항", "포항"),
    WJU("원주공항", "원주"),
    KUV("군산공항", "군산"),
    MWX("무안국제공항", "무안"),
    ICN("인천국제공항", "인천"),
    ;

    private final String label;
    private final String city;

    Airport(String label, String city) {
        this.label = label;
        this.city = city;
    }

    public String label() {
        if (isSeoul()) {
            return Airport.GMP.label;
        }
        return label;
    }

    public String city() {
        return city;
    }

    public String code() {
        if (isSeoul()) {
            return Airport.GMP.name();
        }
        return name();
    }

    private boolean isSeoul() {
        return this == Airport.SEL;
    }

    public static Airport fromCity(String city) {
        for (Airport airport : Airport.values()) {
            if (airport.city.equals(city)) {
                return airport;
            }
        }
        throw new IllegalArgumentException("Invalid city: " + city);
    }
}
