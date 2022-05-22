package com.toh.database.core;

import java.lang.reflect.InvocationTargetException;

public class MappedField<T extends BaseEntity> {
    protected Class<T> type;
    
    protected Repository<T> findRepository() {
        Repository<T> repository = null;
        try {
            String repositoryName =
                    MappedList.class.getPackageName()
                            .replace(".core", ".repository.") +
                            type.getSimpleName() + "Repository";

            repository = (Repository<T>) Class.forName(repositoryName).getMethod("execute").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return repository;
    }
}
