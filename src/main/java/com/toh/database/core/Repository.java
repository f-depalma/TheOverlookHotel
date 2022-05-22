package com.toh.database.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;


public class Repository<T extends BaseEntity> {
    protected Connector<T> connector;
    protected ArrayList<T> data;

    protected Repository(Class<T> type, String fileName) {
        connector = new Connector<>(type, fileName);
        data = connector.load();
    }

    public T findById(int id) {
        return data.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }

    public ArrayList<T> getAll() {
        return data;
    }

    public void save(T entity) {
        int maxId;

        if (!data.removeIf(f -> f.getId() == entity.getId())) {
            try {
                maxId = data.stream().max(Comparator.comparing(BaseEntity::getId)).orElseThrow().getId();
            } catch (NoSuchElementException e) {
                maxId = 0;
            }
            entity.setId(maxId + 1);
        }

        data.add(entity);
    }

    public void flush(){
        connector.flush(data);
    }

    public void saveAndFlush(T entity) {
        save(entity);
        flush();
    }
}
