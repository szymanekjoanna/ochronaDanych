package com.od.cryptography.errors;

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
