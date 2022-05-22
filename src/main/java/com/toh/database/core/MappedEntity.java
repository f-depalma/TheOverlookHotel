package com.toh.database.core;

public class MappedEntity<T extends BaseEntity> extends MappedField<T> {
    private Integer id;

    public MappedEntity(Class<T> type) {
        this.type = type;
    }

    public T getValue() {
        if (id != null) {
            T value = findRepository().findById(id);
            if (value == null) {
                id = null;
            }
            return value;
        }
        return null;
    }

    public void setValue(T value) {
        this.id = value.getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
}
