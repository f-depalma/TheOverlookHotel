package com.toh.database.core.Exceptions;

public class UnsavedEntityException extends Exception {
    public UnsavedEntityException() {
        super("The entity must be saved before to be set");
    }
}
