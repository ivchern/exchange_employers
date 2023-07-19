package com.ivchern.exchange_employers.Common;

import lombok.Getter;
import lombok.Setter;

public enum ErrorCodeEnum {
    SUCCESS(200, "success"),

    FAILED(405, "failed"),

    ILLEGAL_ARGUMENT(409, "illegal argument"),

    LOGIN_FAILED(401, "login failed");

    @Getter
    private final int code;
    @Setter
    @Getter
    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
