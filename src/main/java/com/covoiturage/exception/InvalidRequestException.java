package com.covoiturage.exception;

public class InvalidRequestException extends BusinessRuleException {
    public InvalidRequestException(String message) { super(message); }
}
