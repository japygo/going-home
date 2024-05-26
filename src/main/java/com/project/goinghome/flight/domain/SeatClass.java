package com.project.goinghome.flight.domain;

public enum SeatClass {
    Y("일반석"),
    D("할인석"),
    L("특가석"),
    C("비즈니스석"),
    ;

    private final String label;

    SeatClass(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
