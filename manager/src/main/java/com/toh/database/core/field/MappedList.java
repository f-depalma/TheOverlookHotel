package com.toh.database.core.field;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.Exceptions.UnsavedEntityException;
import com.toh.database.core.Repository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class MappedList<T extends BaseEntity> extends MappedField<T> {
    private ArrayList<Integer> ids = new ArrayList<>();

    public MappedList(Class<T> type) {
        super(type);
    }

    @Override
    public ArrayList<T> getValue() {
        // get the values from the IDs
        if (ids.size() > 0) {
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

    public void setValue(ArrayList<T> value) throws UnsavedEntityException {
        ids = value.stream()
                .map(BaseEntity::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));

        if (value.stream().anyMatch(f -> f.getId() == null)) {
            throw new UnsavedEntityException(type);
        }
    }


    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public ArrayList<Integer> getIds() {
        return this.ids;
    }

    public void save(T entity) {
        this.ids.removeIf(id -> id.equals(entity.getId()));
        this.ids.add(entity.getId());
    }

    public void remove(Integer id) {
        this.ids.removeIf(f -> f.equals(id));
    }
}
