package com.botscrew.test.exception;

public class DepartmentDoesNotExistException extends RuntimeException {

    public DepartmentDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
