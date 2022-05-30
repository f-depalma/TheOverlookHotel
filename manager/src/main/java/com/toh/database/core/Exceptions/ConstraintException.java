package com.toh.database.core.Exceptions;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.Repository;

public class ConstraintException extends Exception {
    public ConstraintException(BaseEntity entity, String fieldName) {
        super("The entity \"" + entity.getClass().getSimpleName() + "\" (ID: " + entity.getId() + ") can not be deleted " +
                "(\"" + fieldName +"\" field can not be null or empty)\n" +
                Repository.getRepository(entity.getClass()).getConnector().objToJson(entity, "\t"));
    }
}
