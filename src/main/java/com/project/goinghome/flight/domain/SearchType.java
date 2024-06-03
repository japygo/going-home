package com.project.goinghome.flight.domain;

public enum SearchType {
    D("기본"),
    P("기간"),
    ;

    private final String label;

    SearchType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
