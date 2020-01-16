package com.od.cryptography.common.errors;

public class ValidationError extends Exception {

    private String message;

    public ValidationError(){
        super();
    }

    public ValidationError(String message){
        super();
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
