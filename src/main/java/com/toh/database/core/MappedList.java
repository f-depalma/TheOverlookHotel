package com.toh.database.core;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MappedList<T extends BaseEntity> extends MappedField<T> {
    private ArrayList<Integer> ids;
    private ArrayList<T> value = null;

    public MappedList(Class<T> clazz) {
        this.type = clazz;
    }

    public ArrayList<T> getValue() {
        if (value == null) {
            value = new ArrayList<>();
            Repository<T> repository = findRepository();

            for (int id : ids) {
                T entity = repository.findById(id);
                value.add(entity);
            }
        }
        return value;
    }

    public void setValue(ArrayList<T> value) {
        this.value = value;
        this.ids = new ArrayList<>(value.stream().map(f -> f.getId()).collect(Collectors.toList()));
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public ArrayList<Integer> getIds() {
        return this.ids;
    }

    public void save(T entity) {
        this.ids.removeIf(id -> id == entity.getId());
        this.value.removeIf(e -> e.getId() == entity.getId());
        this.ids.add(entity.getId());
        this.value.add(entity);
    }

    public void remove(int id) {
        this.ids.removeIf(f -> f == id);
        this.value.removeIf(f -> f.getId() == id);
    }
}
