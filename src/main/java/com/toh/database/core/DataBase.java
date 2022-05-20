package com.toh.database.core;

import com.toh.database.entity.Date;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.FileNotFoundException;


public class DataBase<T extends BaseEntity> {
    private ArrayList<T> list = new ArrayList<>();
    private Class<T> type;

    public DataBase(Class<T> clazz, String fileName) {
        this.type = clazz;
        load(fileName);
    }

    public ArrayList<T> get() {
        return list;
    }

    private void load(String fileName) {
        Scanner read = openFile(fileName);
        T obj = null;
        String str = "";
        read.nextLine();

        while (read.hasNext()) {
            str = getNextLine(read);

            if (str.equals("{")) {
                try {
                    obj = type.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else if (str.equals("}")) {
                list.add(obj);
            } else if (str.contains("[")) {
                String field = str.split(":")[0];
                ArrayList<Integer> idList = new ArrayList<>();

                str = getNextLine(read);
                while (!str.equals("]")) {
                    idList.add(Integer.parseInt(str));
                    str = getNextLine(read);
                }

                try {
                    Field mappedList = type.getDeclaredField(field);
                    mappedList.setAccessible(true);
                    MappedList.class.getMethod("setIds", ArrayList.class)
                            .invoke(mappedList.get(obj), idList);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

            } else if (!str.equals("[") && !str.equals("]")) {
                String[] splitted = str.split(":");
                String field = splitted[0];
                String value = splitted[1].trim();

                Class parClass = null;

                try {
                    parClass = type.getDeclaredField(splitted[0]).getType();
                } catch (NoSuchFieldException e) {
                    try {
                        parClass = BaseEntity.class.getDeclaredField(splitted[0]).getType();
                    } catch (NoSuchFieldException ex) {
                        ex.printStackTrace();
                    }
                }

                try {
                    switch (parClass.getSimpleName()) {
                        case "int":
                            type.getMethod(getMethodName(field), parClass)
                                    .invoke(obj, Integer.parseInt(value));
                            break;
                        case "double":
                            type.getMethod(getMethodName(field), parClass)
                                    .invoke(obj, Double.parseDouble(value));
                            break;
                        case "MappedField": {
                            Field mappedField = type.getDeclaredField(field);
                            mappedField.setAccessible(true);
                            MappedField.class.getMethod("setId", int.class)
                                    .invoke(mappedField.get(obj), Integer.parseInt(value));
                            break;
                        }
                        case "Date": {
                            Field mappedField = type.getDeclaredField(field);
                            mappedField.setAccessible(true);
                            Date.class.getMethod("set", String.class)
                                    .invoke(mappedField.get(obj), value);
                            break;
                        }
                        default:
                            type.getMethod(getMethodName(field), parClass)
                                    .invoke(obj, value);
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        read.close();
    }

    private static Scanner openFile(String fileName) {
        Scanner read = null;
        try {
            FileInputStream fileIn = new FileInputStream("./src/main/java/com/toh/database/files/" + fileName);
            read = new Scanner(fileIn);
        } catch (FileNotFoundException e) {
            System.out.println("File not found, or could not be opened");
            System.exit(1);
        }
        return read;
    }

    private String getNextLine(Scanner read) {
        return read.nextLine().trim().replace("\"", "").replace(",", "");
    }

    private String getMethodName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}

