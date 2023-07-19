package com.ivchern.exchange_employers.Common.Exception;

public class ForbiddenException extends RuntimeException {

        public ForbiddenException() {
            this("Forbidden operation");
        }

        /**
         * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
         * initialized, and may subsequently be initialized by a call to {@link #initCause}.
         */
        public ForbiddenException(String msg) {
            super(msg);
        }

    }
