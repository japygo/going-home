package com.project.goinghome.flight.domain;

public enum Airline {
    KAL("대한항공", "KE"),
    AAR("아시아나항공", "OZ"),
    JJA("제주항공", "7C"),
    JNA("진에어", "LJ"),
    ABL("에어부산", "BX"),
    ESR("이스타항공", "ZE"),
    TWB("티웨이항공", "TW"),
    ASV("에어서울", "RS"),
    FGW("플라이강원", "4V"),
    HGG("하이에어", "4H"),
    AIH("에어인천", "KJ"),
    APZ("에어프레미아", "YP"),
    EOK("에어로케이", "RF"),
    ;

    private final String label;
    private final String code;

    Airline(String label, String code) {
        this.label = label;
        this.code = code;
    }

    public String label() {
        return label;
    }

    public String code() {
        return code;
    }

    public static Airline fromCode(String code) {
        for (Airline airline : Airline.values()) {
            if (airline.code.equals(code)) {
                return airline;
            }
        }
        throw new IllegalArgumentException("Invalid airline code: " + code);
    }
}
