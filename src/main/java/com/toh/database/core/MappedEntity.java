package com.toh.database.core;

public class MappedEntity<T extends BaseEntity> extends MappedField<T> {
    private T value;
    private Integer id;

    public MappedEntity(Class<T> type) {
        this.type = type;
    }

    public T getValue() {
        if (value == null) {
            value = findRepository().findById(id);
        }
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.id = value.getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
}
