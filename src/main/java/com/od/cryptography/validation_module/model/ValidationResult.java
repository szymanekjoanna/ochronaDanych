package com.od.cryptography.validation_module.model;

public class ValidationResult {

    private String message;
    private Boolean isValid;

    public ValidationResult(String message) {
        this.message = message;
    }

    public ValidationResult(String message, Boolean isValid) {
        this.message = message;
        this.isValid = isValid;
    }

    public ValidationResult(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
