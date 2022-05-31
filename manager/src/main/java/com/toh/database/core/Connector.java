package com.toh.database.core;

import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.core.field.Date;
import com.toh.database.core.field.MappedEntity;
import com.toh.database.core.field.MappedList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Connector<T extends BaseEntity> {
    private final Class<T> type;
    private final String fileName;
    private final List<Field> fields;
    private static final String filePath = "./src/main/resources/com/toh/data/";

    public Connector(Class<T> type, String fileName) {
        this.type = type;
        this.fileName = fileName;
        this.fields = Stream.concat(
                        Arrays.stream(BaseEntity.class.getDeclaredFields())
                                .filter(f -> !Modifier.isStatic(f.getModifiers())),
                        Arrays.stream(type.getDeclaredFields()))
                .collect(Collectors.toList());
    }

    public ArrayList<T> load() {
        ArrayList<T> list = new ArrayList<>();

        try {
            Scanner read = new Scanner(Connector.class.getResourceAsStream(fileName));
            T obj = null;
            String str;
            if (read.hasNext()) {
                read.nextLine();
            }

            while (read.hasNext()) {
                str = getNextLine(read);

                if (str.equals("]")) {
                    read.close();
                    break;
                } else if (str.equals("{")) {
                    try {
                        obj = type.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                        break;
                    }
                } else if (str.contains("}")) {
                    list.add(obj);
                } else if (str.contains("[")) {
                    String field = str.split(":")[0];
                    ArrayList<Integer> idList = new ArrayList<>();

                    if (!str.contains("]")) {
                        str = getNextLine(read);
                        while (!str.equals("]")) {
                            idList.add(Integer.parseInt(str));
                            str = getNextLine(read);
                        }
                    }

                    try {
                        Field mappedList = type.getDeclaredField(field);
                        mappedList.setAccessible(true);
                        MappedList.class.getMethod("setIds", ArrayList.class)
                                .invoke(mappedList.get(obj), idList);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }

                } else {
                    String[] splitted = str.split(":");
                    String field = splitted[0];
                    String value = splitted[1].trim();

                    if (!value.equals("null")) {
                        Class<?> parClass;

                        try {
                            parClass = type.getDeclaredField(splitted[0]).getType();
                        } catch (NoSuchFieldException e) {
                            try {
                                parClass = BaseEntity.class.getDeclaredField(splitted[0]).getType();
                            } catch (NoSuchFieldException ex) {
                                ex.printStackTrace();
                                continue;
                            }
                        }

                        try {
                            if (Integer.class.equals(parClass)) {
                                type.getMethod(getMethodName(field), parClass)
                                        .invoke(obj, Integer.valueOf(value));
                            } else if (Double.class.equals(parClass)) {
                                type.getMethod(getMethodName(field), parClass)
                                        .invoke(obj, Double.valueOf(value));
                            } else if (Boolean.class.equals(parClass)) {
                                type.getMethod(getMethodName(field), parClass)
                                        .invoke(obj, Boolean.valueOf(value));
                            } else if (MappedEntity.class.equals(parClass)) {
                                Field mappedField = type.getDeclaredField(field);
                                mappedField.setAccessible(true);
                                MappedEntity.class.getMethod("setId", Integer.class)
                                        .invoke(mappedField.get(obj), Integer.valueOf(value));
                            } else if (Date.class.equals(parClass)) {
                                Field mappedField = type.getDeclaredField(field);
                                mappedField.setAccessible(true);
                                Date.class.getMethod("set", String.class)
                                        .invoke(mappedField.get(obj), value);
                            } else {
                                type.getMethod(getMethodName(field), parClass)
                                        .invoke(obj, value);
                            }
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("File " + fileName + " not found, or could not be opened");
        }

        return list;
    }

    private String getNextLine(Scanner read) {
        return read.nextLine().trim().replace("\"", "").replace(",", "");
    }

    private String getMethodName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public void flush(ArrayList<T> list) {
        if (list.size() > 0) {
            list.sort(Comparator.comparing(BaseEntity::getId));
            FileOutputStream fileOut;

            try {
                fileOut = new FileOutputStream(filePath + fileName);
                PrintWriter write = new PrintWriter(fileOut);
                write.println("[");
                int i;
                for (i = 0; i < list.size() - 1; i++) {
                    try {
                        checkValidity(list.get(i));
                        write.print(objToJson(list.get(i), "  "));
                        write.println(",");
                    } catch (EntityNotValidException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    checkValidity(list.get(i));
                    write.print(objToJson(list.get(i), "  "));
                } catch (EntityNotValidException e) {
                    e.printStackTrace();
                }
                write.println();
                write.print("]");
                write.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkValidity(T entity) throws EntityNotValidException {
        if (!entity.isValid()) {
            throw new EntityNotValidException(entity);
        }
    }

    public String objToJson(Object obj, String padding) {
        StringBuilder json = new StringBuilder(padding + "{\n");

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (field.getType().equals(MappedList.class)) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> ids = (ArrayList<Integer>) MappedList.class.getMethod("getIds").invoke(value);
                    if (ids != null && ids.size() > 0) {
                        json.append(padding).append("  \"").append(field.getName()).append("\": [\n");

                        for (int i = 0; i < ids.size(); i++) {
                            json.append(padding).append("    ").append(ids.get(i)).append(i + 1 < ids.size() ? "," : "").append("\n");
                        }
                        json.append(padding).append("  ],\n");
                    }
                } else if (field.getType().equals(MappedEntity.class)) {
                    Integer id = (Integer) MappedEntity.class.getMethod("getId").invoke(value);
                    if (id != null) {
                        json.append(padding).append("  \"").append(field.getName()).append("\": ").append(id).append(",\n");
                    }
                } else if (field.getType().equals(Date.class)) {
                    if (value.toString() != null) {
                        json.append(padding).append("  \"").append(field.getName()).append("\": \"").append(value).append("\",\n");
                    }
                } else {
                    if (value != null) {
                        json.append(padding).append("  \"").append(field.getName()).append("\": ");
                        if (Number.class.isAssignableFrom(field.getType())) {
                            json.append(value).append(",\n");
                        } else {
                            json.append("\"").append(value).append("\",\n");
                        }
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        json = new StringBuilder(json.substring(0, json.length() - 2) + "\n" + padding + "}");
        return json.toString();
    }
}