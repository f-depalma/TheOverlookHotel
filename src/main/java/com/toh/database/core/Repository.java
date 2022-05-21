package com.toh.database.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        int index = IntStream.range(0, data.size())
                .filter(i -> data.get(i).getId() == entity.getId())
                .findFirst()
                .orElse(-1);

        if (index != -1) {
            data.removeIf(f -> f.getId() == entity.getId());
            connector.update(index, entity);
        } else {
            int maxId = data.stream().max(Comparator.comparing(BaseEntity::getId))
                    .orElseThrow(NoSuchElementException::new).getId();
            entity.setId(maxId + 1);
            data.add(entity);
            connector.add(entity);
        }
    }
}
