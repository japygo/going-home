package com.project.goinghome.flight.domain;

public enum FareType {
    YC("전체"),
    Y("일반/할인/특가석"),
    C("비즈니스석"),
    ;

    private final String label;

    FareType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
