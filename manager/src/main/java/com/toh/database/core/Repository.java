package com.toh.database.core;

import com.toh.database.core.Exceptions.ConstraintException;
import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.core.field.MappedEntity;
import com.toh.database.core.field.MappedField;
import com.toh.database.core.field.MappedList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;


public class Repository<T extends BaseEntity> {
    protected Connector<T> connector;
    protected ArrayList<T> data;
    private final Class<T> type;

    protected Repository(Class<T> type, String fileName) {
        this.type = type;
        connector = new Connector<>(type, fileName);
        data = connector.load();
    }

    public T findById(int id) {
        return data.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }

    public ArrayList<T> getAll() {
        return data;
    }

    public ArrayList<T> getAll(boolean copy) {
        if (copy) {
            return new ArrayList<T>(data);
        } else {
            return data;
        }
    }

    public void save(T entity) throws EntityNotValidException {
        if (entity.isValid()) { // if the entity is valid
            if (!_delete(entity.getId())) { // if it cannot be deleted from the list means is a new entity else delete it and save again into the list
                entity.setId(generateId());
            }
            data.add(entity); // add to the list
        } else {
            throw new EntityNotValidException(entity);
        }
    }

    public void flush() {
        connector.flush(data); // update the file
    }

    public void saveAndFlush(T entity) throws EntityNotValidException {
        save(entity);
        flush();
    }

    private boolean _delete(Integer id) {
        return this.data.removeIf(f -> f.getId().equals(id));
    }

    public void delete(Integer id) throws ConstraintException {
        String fieldName = this.type.getSimpleName().toLowerCase();
        List<Class<?>> constrainstList = BaseEntity.getNotNullField().entrySet().stream().filter(entry -> entry.getValue().stream().anyMatch(field -> field.equals(fieldName) || field.equals(fieldName + "List"))) // I get only the entity class that have this entity as not null field
                .map(entry -> entry.getKey()) // I get the class of them
                .collect(Collectors.toList());


        for (Class<?> type : constrainstList) { // for each class that have a not null field instanceof this.type
            for (Object entity : getRepository(type).getAll()) { // for each entity of this classes
                try {
                    String methodName = "get" + this.type.getSimpleName();
                    Field field;

                    //TODO: maybe it can be written better 84:90
                    try { // if is not a MappedList
                        field = type.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) { // if is a MappedList
                        field = type.getDeclaredField(fieldName + "List");
                    }

                    Class<MappedField> fieldType = (Class<MappedField>) field.getType(); // get the field class

                    // I check only the MappedEntity field becouse I'm trying to delete its reference id;
                    if (fieldType.equals(MappedEntity.class)) {
                        // If the entity is related to the deleted object throw an exception
                        if (((BaseEntity) type.getDeclaredMethod(methodName).invoke(entity)).getId().equals(id)) { // I retrieve the id value of the class of the entity deleted
                            throw new ConstraintException((BaseEntity) entity, field.getName());
                        }
                    } else if (fieldType.equals(MappedList.class)) {
                        methodName += "List";
                        ArrayList<BaseEntity> entityList = ((ArrayList<BaseEntity>) type.getDeclaredMethod(methodName).invoke(entity));
//                      If the only reference in the list is the id of the deleted entity
                        if (entityList.stream().anyMatch(e -> e.getId().equals(id)) && entityList.size() == 1) {
                            throw new ConstraintException((BaseEntity) entity, field.getName());
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        _delete(id); // if no ConstraintException have been thrown delete the entity
    }

    public boolean contains(Integer id) {
        return this.data.stream().anyMatch(d -> d.getId().equals(id));
    }

    public Integer generateId() {
        Integer maxId;
        try {
            maxId = data.stream().max(Comparator.comparing(BaseEntity::getId)).orElseThrow().getId();
        } catch (NoSuchElementException e) {
            maxId = 0;
        }
        return maxId + 1;
    }

    public static Repository getRepository(Class<?> type) {
        Repository repository = null;
        try {
            // do not move the repositories from its path
            String repositoryName = MappedField.class.getPackageName().replace("field", type.getSimpleName() + "Repository").replace("core", "repository");

            repository = (Repository) Class.forName(repositoryName).getMethod("execute").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return repository;
    }

    public Connector<T> getConnector() {
        return connector;
    }
}
