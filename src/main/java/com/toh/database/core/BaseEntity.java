package com.toh.database.core;

import com.toh.database.core.field.Date;
import com.toh.database.core.field.MappedField;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class BaseEntity {
    private Integer id;
    protected static HashMap<Class<? extends BaseEntity>, List<String>> notNullFields = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected static void notNullFields(Class<? extends BaseEntity> type, String... fields) {
        notNullFields.put(type, Arrays.asList(fields));
    }

    public static HashMap<Class<? extends BaseEntity>, List<String>> getNotNullField() {
        return notNullFields;
    }

    public boolean isValid() {
        for (String fieldName : notNullFields.get(this.getClass())) {
            try {
                Field field = this.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(this);

                if (value == null
                        || (value.getClass().equals(Date.class) && !((Date) value).isValid())
                        || (value.getClass().getSuperclass().equals(MappedField.class) && !((MappedField) value).isValid())) {
                    return false;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("Field " + fieldName + " not found in class " + this.getClass().getSimpleName());
            }
        }
        return true;
    }
}