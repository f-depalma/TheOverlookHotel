package com.toh.database.core;

import java.lang.reflect.InvocationTargetException;

public class MappedField<T extends BaseEntity> {

    private Integer id;
    private T value;
    private Class<T> type;

    public MappedField(Class<T> type) {
        this.type = type;
    }

    public T getValue() {
        if (value == null) {
            Repository<T> repository = null;
            try {
                repository = (Repository<T>) Class.forName(type.getName()+"Repository").getMethod("get").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            value = repository.findById(id);
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

    public Integer getId() { return this.id; }
}
