package com.toh.database.core.Exceptions;

import com.toh.database.core.BaseEntity;

public class UnsavedEntityException extends Exception {
    public UnsavedEntityException(Class<? extends BaseEntity> type) {
        super("The entity " + type.getSimpleName() + " must be saved before to be set");
    }
}
