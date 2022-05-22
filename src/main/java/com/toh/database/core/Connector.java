package com.toh.database.core;

import com.toh.database.entity.Date;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.FileNotFoundException;
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

    public void update(T entity) {
        try {
            RandomAccessFile file = new RandomAccessFile(filePath + fileName, "rw");
            int i = 0;
            String str = "";
            do {
                i += str.length() + 1;
                str = file.readLine();
            } while (!str.equals("    \"id\": " + entity.getId() + ","));
            file.seek(i - 5);
            file.writeBytes(objToJson(entity));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(T entity) {
        try {
            RandomAccessFile file = new RandomAccessFile(filePath + fileName, "rw");

            if (file.length() > 2) {
                file.seek(file.length() - 2);
                file.writeBytes(",\n");
            } else {
                file.writeBytes("[\n");
            }
            file.writeBytes(objToJson(entity));
            file.writeBytes("\n]");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                    json += "    \"" + field.getName() + "\": [";

                    if (ids != null) {
                        json += "\n";
                        for (int i = 0; i < ids.size(); i++) {
                            json += "      " + ids.get(i) + (i + 1 < ids.size() ? "," : "") + "\n";
                        }
                        json += "    ";
                    }
                    json += "],\n";
                } else if (field.getType().equals(MappedEntity.class)) {
                    json += "    \"" + field.getName() + "\": " + MappedEntity.class.getMethod("getId").invoke(value) + ",\n";
                } else {
                    json += "    \"" + field.getName() + "\": ";
                    if (!Number.class.isAssignableFrom(field.getType()) && value != null && value.toString() != null) {
                        json += "\"" + value + "\",\n";
                    } else {
                        json += value + ",\n";
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