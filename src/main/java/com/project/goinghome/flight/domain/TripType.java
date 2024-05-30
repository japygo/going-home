package com.project.goinghome.flight.domain;

public enum TripType {
    OW("편도"),
    RT("왕복"),
    ;

    private final String label;

    TripType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
