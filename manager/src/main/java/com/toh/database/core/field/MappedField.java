package com.toh.database.core.field;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.Repository;

public abstract class MappedField<T extends BaseEntity> {
    protected Class<T> type;

    protected MappedField(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    protected Repository<T> findRepository() {
        return Repository.getRepository(this.type);
    }

    public abstract Object getValue();

    public boolean isValid() {
        return getValue() != null;
    }
}
