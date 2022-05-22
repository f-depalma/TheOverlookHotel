package com.toh.database.core;

import com.toh.database.entity.Date;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Connector<T extends BaseEntity> {
    private Class<T> type;
    private String fileName;
    private List<Field> fields;
    private static final String filePath = "./src/main/java/com/toh/database/files/";

    public Connector(Class<T> clazz, String fileName) {
        this.type = clazz;
        this.fileName = fileName;
        this.fields = Stream.concat(
                        Arrays.stream(BaseEntity.class.getDeclaredFields()),
                        Arrays.stream(type.getDeclaredFields()))
                .collect(Collectors.toList());
    }

    public ArrayList<T> load() {
        ArrayList<T> list = new ArrayList<>();
        Scanner read = openFile(fileName);
        T obj = null;
        String str = "";
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
                            MappedEntity.class.getMethod("setId", int.class)
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
        return list;
    }

    private static Scanner openFile(String fileName) {
        Scanner read = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath + fileName);
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

    public void flush(ArrayList<T> list) {
        if (list.size() > 0) {
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(filePath + fileName);
                PrintWriter write = new PrintWriter(fileOut);
                write.println("[");
                int i;
                for (i = 0; i < list.size() - 1; i++) {
                    write.print(objToJson(list.get(i)));
                    write.println(",");
                }
                write.print(objToJson(list.get(i)));
                write.println();
                write.print("]");
                write.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String objToJson(T obj) {
        String json = "  {\n";

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (field.getType().equals(MappedList.class)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) MappedList.class.getMethod("getIds").invoke(value);
                    if (ids != null && ids.size() > 0) {
                        json += "    \"" + field.getName() + "\": [\n";

                        for (int i = 0; i < ids.size(); i++) {
                            json += "      " + ids.get(i) + (i + 1 < ids.size() ? "," : "") + "\n";
                        }
                        json += "    ],\n";
                    }
                } else if (field.getType().equals(MappedEntity.class)) {
                    Integer id = (Integer) MappedEntity.class.getMethod("getId").invoke(value);
                    if (id != null) {
                        json += "    \"" + field.getName() + "\": " + id + ",\n";
                    }
                } else if (field.getType().equals(Date.class)) {
                    if (value.toString() != null) {
                        json += "    \"" + field.getName() + "\": \"" + value + "\",\n";
                    }
                } else {
                    if (value != null) {
                        json += "    \"" + field.getName() + "\": ";
                        if (Number.class.isAssignableFrom(field.getType())) {
                            json += value + ",\n";
                        } else {
                            json += "\"" + value + "\",\n";
                        }
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        json = json.substring(0, json.length() - 2) + "\n  }";
        return json;
    }
}