package com.toh.database.core.field;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.Exceptions.UnsavedEntityException;

public class MappedEntity<T extends BaseEntity> extends MappedField<T> {
    private Integer id;

    public MappedEntity(Class<T> type) {
        super(type);
    }

    @Override
    public T getValue() {
        T value = findRepository().findById(id);
        if (value == null) {
            id = null;
        }

        return value;
    }

    public void setValue(T entity) throws UnsavedEntityException {
        if (entity.getId() == null) {
            throw new UnsavedEntityException(type);
        }
        id = entity.getId();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
}
