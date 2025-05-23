package org.example.scheduledevelop.exception;

public class EmailOrPasswordDoesNotMatch extends RuntimeException{
    public EmailOrPasswordDoesNotMatch(String message){
        super(message);
    }
}
