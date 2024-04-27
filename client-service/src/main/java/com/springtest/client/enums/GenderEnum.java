package com.springtest.client.enums;

public enum GenderEnum {
    MASCULINO("MASCULINO"),
    FEMENINO("FEMENINO");

    private final String status;

    GenderEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

}
