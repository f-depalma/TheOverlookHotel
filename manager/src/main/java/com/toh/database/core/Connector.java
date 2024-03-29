package com.toh.database.core;

import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.core.field.Date;
import com.toh.database.core.field.MappedEntity;
import com.toh.database.core.field.MappedList;

import java.io.*;
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
    private static final String filePath = "./src/main/java/com/toh/database/core/data/";

    public Connector(Class<T> type, String fileName) {
        this.type = type;
        this.fileName = fileName;
        //get the non-static fields from the entity class and from the BaseEntity
        this.fields = Stream.concat(
                        Arrays.stream(BaseEntity.class.getDeclaredFields())
                                .filter(f -> !Modifier.isStatic(f.getModifiers())),
                        Arrays.stream(type.getDeclaredFields()))
                .collect(Collectors.toList());
    }

    public ArrayList<T> load() {
        ArrayList<T> list = new ArrayList<>();

        try {
            // open the resouce and start reading it
            FileInputStream fileIn = new FileInputStream(filePath + fileName);
            Scanner read = new Scanner(fileIn);
            T obj = null;
            String str;
            //skip the first row (”[")
            if (read.hasNext()) {
                read.nextLine();
            }

            while (read.hasNext()) {
                str = getNextLine(read);

                if (str.equals("]")) { // if is the end of the file
                    read.close();
                    break;
                } else if (str.equals("{")) { // if is a new object get the constructor and instantiate it
                    try {
                        obj = type.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                        break;
                    }
                } else if (str.contains("}")) { // if is the end of the object add it to the list
                    list.add(obj);
                } else if (str.contains("[")) { // if is a list of ids (one to many)
                    String field = str.split(":")[0];
                    ArrayList<Integer> idList = new ArrayList<>();

                    if (!str.contains("]")) { // if is not an empty list
                        str = getNextLine(read);
                        while (!str.equals("]")) { // loop while the list is not finish
                            idList.add(Integer.parseInt(str)); //add the id tu the list
                            str = getNextLine(read);
                        }
                    }

                    try {
                        //set values to the object
                        Field mappedList = type.getDeclaredField(field);
                        mappedList.setAccessible(true);
                        MappedList.class.getMethod("setIds", ArrayList.class)
                                .invoke(mappedList.get(obj), idList);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                } else { // if a String | Number
                    String[] splitted = str.split(":");
                    String field = splitted[0];
                    String value = splitted[1].trim();

                    if (!value.equals("null")) {
                        Class<?> parClass;

                        try {
                            parClass = type.getDeclaredField(splitted[0]).getType(); // get the value type from the entity class
                        } catch (NoSuchFieldException e) {
                            try { // try to find the field into the super class (BaseEntity)
                                parClass = BaseEntity.class.getDeclaredField(splitted[0]).getType();
                            } catch (NoSuchFieldException ex) {
                                ex.printStackTrace();
                                continue;
                            }
                        }

                        try { // set the value to the object
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
        } catch (NullPointerException | FileNotFoundException e) {
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

    // save the entities to the file
    public void flush(ArrayList<T> list) {
        if (list.size() > 0) {
            list.sort(Comparator.comparing(BaseEntity::getId));
            FileOutputStream fileOut;

            try {
                // I override the file
                fileOut = new FileOutputStream(filePath + fileName);
                PrintWriter write = new PrintWriter(fileOut);
                write.println("["); // open the list
                int i;
                for (i = 0; i < list.size() - 1; i++) { // loop on the entity
                    try {
                        checkValidity(list.get(i)); // check if they are valid
                        write.print(objToJson(list.get(i), "  ")); // convert entity into json
                        write.println(",");
                    } catch (EntityNotValidException e) {
                        e.printStackTrace();
                    }
                }
                try { // write the last one without the common
                    checkValidity(list.get(i));
                    write.print(objToJson(list.get(i), "  "));
                } catch (EntityNotValidException e) {
                    e.printStackTrace();
                }
                write.println();
                write.print("]"); // close the list
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
        StringBuilder json = new StringBuilder(padding + "{\n"); // open the object

        for (Field field : fields) { // loop on the field
            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (field.getType().equals(MappedList.class)) { // if is a list of id
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> ids = (ArrayList<Integer>) MappedList.class.getMethod("getIds").invoke(value);
                    if (ids != null && ids.size() > 0) { // if the list is not empty
                        json.append(padding).append("  \"").append(field.getName()).append("\": [\n"); // write the field name and the open of the list

                        for (int i = 0; i < ids.size(); i++) { // loop on the ids in the list
                            json.append(padding).append("    ").append(ids.get(i)).append(i + 1 < ids.size() ? "," : "").append("\n"); // write the id
                        }
                        json.append(padding).append("  ],\n"); // close the list
                    }
                } else if (field.getType().equals(MappedEntity.class)) { // if is one to one relation (reference id to another entity)
                    Integer id = (Integer) MappedEntity.class.getMethod("getId").invoke(value);
                    if (id != null) { // if is not null
                        json.append(padding).append("  \"").append(field.getName()).append("\": ").append(id).append(",\n"); // write the field name and the id
                    }
                } else if (field.getType().equals(Date.class)) { // if is a date
                    if (value.toString() != null) { // if is not null
                        json.append(padding).append("  \"").append(field.getName()).append("\": \"").append(value).append("\",\n"); // write the field name and the date
                    }
                } else { // other type of value (String | Number)
                    if (value != null) { //if is not null
                        json.append(padding).append("  \"").append(field.getName()).append("\": "); // write the fild name
                        if (Number.class.isAssignableFrom(field.getType())) { // if is number
                            json.append(value).append(",\n"); // write the value
                        } else {
                            json.append("\"").append(value).append("\",\n"); // else add the " before and after the value
                        }
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        json = new StringBuilder(json.substring(0, json.length() - 2) + "\n" + padding + "}"); // close the object
        return json.toString();
    }
}