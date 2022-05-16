package com.toh.database.repository;

import com.toh.database.DataBase;
import com.toh.database.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository<T extends BaseEntity> {
    DataBase<T> dataBase;

    Repository(Class<T> clazz, String fileName) {
        dataBase = new DataBase<>(clazz, fileName);
    }

    public T findById(int id) {
        List<T> result = dataBase.get().stream().filter(f -> f.getId() == id).collect(Collectors.toList());
        return result.size() > 0? result.get(0) : null;
    }

    public ArrayList<T> getAll() {
        return dataBase.get();
    }
}
