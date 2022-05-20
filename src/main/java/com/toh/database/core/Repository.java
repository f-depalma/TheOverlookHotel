package com.toh.database.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository<T extends BaseEntity> {
    private Connector<T> connector;
    private ArrayList<T> data = new ArrayList<>();

    public Repository(Class<T> clazz, String fileName) {
        connector = new Connector<>(clazz, fileName);
        data = connector.load();
    }

    public T findById(int id) {
        List<T> result = data.stream().filter(f -> f.getId() == id).collect(Collectors.toList());
        return result.size() > 0 ? result.get(0) : null;
    }

    public ArrayList<T> getAll() {
        return data;
    }

    public void save(T entity) {
        data.removeIf(f -> f.getId() == entity.getId());
        data.add(entity);
    }

    public Connector<T> getConnector() {
        return connector;
    }
}
