package com.toh.database.core;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MappedList<T extends BaseEntity> extends MappedField<T> {
    private ArrayList<Integer> ids;

    public MappedList(Class<T> clazz) {
        this.type = clazz;
    }

    public ArrayList<T> getValue() {
        if (ids != null && ids.size() > 0) {
            ArrayList<T> entities = new ArrayList<>();
            Repository<T> repository = findRepository();

            for (int id : ids) {
                T entity = repository.findById(id);
                if (entity != null) {
                    entities.add(entity);
                } else {
                    ids.remove(id);
                }
            }
            return entities;
        }
        return null;
    }

    public void setValue(ArrayList<T> value) {
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
        this.ids.add(entity.getId());
    }

    public void remove(int id) {
        this.ids.removeIf(f -> f == id);
    }
}
