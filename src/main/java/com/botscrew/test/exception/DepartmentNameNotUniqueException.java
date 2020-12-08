package com.botscrew.test.exception;

public class DepartmentNameNotUniqueException extends RuntimeException {

    public DepartmentNameNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}
