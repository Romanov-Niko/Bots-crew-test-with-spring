package com.botscrew.test.exception;

public class SalaryIsNegativeException extends RuntimeException {

    public SalaryIsNegativeException(String errorMessage) {
        super(errorMessage);
    }
}
