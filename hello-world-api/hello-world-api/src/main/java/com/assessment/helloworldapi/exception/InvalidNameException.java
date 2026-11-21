package com.assessment.helloworldapi.exception;

public class InvalidNameException extends RuntimeException{

    public InvalidNameException() {
        super("Invalid Input");
    }

}
