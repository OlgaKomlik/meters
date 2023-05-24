package com.meters.exceptoins;

public class EntityIsDeletedException extends RuntimeException {

    public EntityIsDeletedException(String message) {
        super(message);
    }
}
