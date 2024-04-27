package com.springtest.account.enums;

public enum AccountTypeEnum {
    CORRIENTE("CORRIENTE"),
    AHORRO("AHORRO");

    private final String status;

    AccountTypeEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

}
