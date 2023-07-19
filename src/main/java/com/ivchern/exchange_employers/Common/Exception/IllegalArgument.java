package com.ivchern.exchange_employers.Common.Exception;

import com.ivchern.exchange_employers.Common.ErrorCodeEnum;

public class IllegalArgument extends IllegalArgumentException{
    public IllegalArgument() {
        this("Has been passed an illegal or inappropriate argument");
    }

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public IllegalArgument(String msg) {
        super(msg);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public IllegalArgument(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
    }
}
