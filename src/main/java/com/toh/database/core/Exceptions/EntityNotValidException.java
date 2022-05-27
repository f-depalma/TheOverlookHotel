package com.toh.database.core.Exceptions;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.Repository;

public class EntityNotValidException extends Exception {
    public EntityNotValidException(BaseEntity entity) {
        super("The entity " + entity.getClass().getSimpleName() + " (ID: " + entity.getId() + ") is not valid\n" +
                Repository.getRepository(entity.getClass()).getConnector().objToJson(entity, "\t"));
    }
}
