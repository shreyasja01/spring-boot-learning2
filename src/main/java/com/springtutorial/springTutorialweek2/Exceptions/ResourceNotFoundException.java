package com.springtutorial.springTutorialweek2.Exceptions;


public class ResourceNotFoundException extends  RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
