package com.toh.database.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MappedList<T extends BaseEntity> {

    private ArrayList<Integer> ids;
    private ArrayList<T> value = null;
    private Class<T> type;

    public MappedList(Class<T> clazz) {
        this.type = clazz;
    }

    public ArrayList<T> getValue() {
        if (value == null) {
            Repository<T> repository = null;

            try {
                String repositoryName =
                        MappedList.class.getPackageName()
                                .replace(".core", ".repository.") +
                        type.getSimpleName() + "Repository";

                repository = (Repository<T>) Class.forName(repositoryName).getMethod("get").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            value = new ArrayList<>();
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
}
