package com.toh.database.core;

import java.lang.reflect.InvocationTargetException;

public class MappedField<T extends BaseEntity> {
    protected Class<T> type;
    private Repository<T> repository;

    protected MappedField(Class<T> type) {
        this.type = type;
    }
    
    protected Repository<T> findRepository() {
        if (repository == null) {
            try {
                String repositoryName =
                        MappedField.class.getPackageName()
                                .replace(".core", ".repository.") +
                                type.getSimpleName() + "Repository";

                repository = (Repository<T>) Class.forName(repositoryName).getMethod("execute").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return repository;
    }
}
