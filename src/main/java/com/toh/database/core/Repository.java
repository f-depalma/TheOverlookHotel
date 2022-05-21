package com.toh.database.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;


public class Repository<T extends BaseEntity> {
    private Connector<T> connector;
    private ArrayList<T> data;

    public Repository(Class<T> clazz, String fileName) {
        connector = new Connector<>(clazz, fileName);
        data = connector.load();
    }

    public T findById(int id) {
        return data.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }

    public ArrayList<T> getAll() {
        return data;
    }


    public void save(T entity) {
        if (entity.getId() != null) {
            data.removeIf(f -> f.getId() == entity.getId());
            connector.update(entity);
        } else {
            int maxId;
            try {
                maxId = data.stream().max(Comparator.comparing(BaseEntity::getId)).orElseThrow().getId();
            } catch (NoSuchElementException e) {
                maxId = 0;
            }
            entity.setId(maxId + 1);
            data.add(entity);
            connector.add(entity);
        }
    }
}
