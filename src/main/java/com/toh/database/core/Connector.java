package com.toh.database.core;

import com.toh.database.entity.Date;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Connector<T extends BaseEntity> {
    private Class<T> type;
    private String fileName;

    public Connector(Class<T> clazz, String fileName) {
        this.type = clazz;
        this.fileName = fileName;
    }


    public ArrayList<T> load() {
        ArrayList<T> list = new ArrayList<>();
        Scanner read = openFile(fileName);
        T obj = null;
        String str = "";
        read.nextLine();

        while (read.hasNext()) {
            str = getNextLine(read);

            if (str.contains("]") && !str.contains("[")) {
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
            } else if (str.contains("[") && str.contains("]")) {
                String[] splitted = str.split(":");
                String[] ids = splitted[1]
                        .replace(" ", "")
                        .replace("[", "")
                        .replace("]", "")
                        .split(",");

                ArrayList<Integer> idList = new ArrayList<>();

                for (String id : ids) {
                    idList.add(Integer.parseInt(id));
                }

                try {
                    Field mappedList = type.getDeclaredField(splitted[0]);
                    mappedList.setAccessible(true);
                    MappedList.class.getMethod("setIds", ArrayList.class)
                            .invoke(mappedList.get(obj), idList);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

            } else {
                str = str.replace(",", "");
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
                        case "Integer":
                            type.getMethod(getMethodName(field), parClass)
                                    .invoke(obj, Integer.valueOf(value));
                            break;
                        case "Double":
                            type.getMethod(getMethodName(field), parClass)
                                    .invoke(obj, Double.valueOf(value));
                            break;
                        case "Boolean":
                            type.getMethod(getMethodName(field), parClass)
                                    .invoke(obj, Boolean.valueOf(value));
                            break;
                        case "MappedField": {
                            Field mappedField = type.getDeclaredField(field);
                            mappedField.setAccessible(true);
                            MappedField.class.getMethod("setId", int.class)
                                    .invoke(mappedField.get(obj), Integer.valueOf(value));
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
        return list;
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
        return read.nextLine().trim().replace("\"", "");
    }

    private String getMethodName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public void save(T entity) {

    }

    public String ObjToJson(T obj) {
        String json = "{\n";
        List<Field> fields = Stream.concat(
                        Arrays.stream(BaseEntity.class.getDeclaredFields()),
                        Arrays.stream(obj.getClass().getDeclaredFields()))
                .collect(Collectors.toList());
        ;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (field.getType().equals(MappedList.class)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) MappedList.class.getMethod("getIds").invoke(value);
                    json += "\t\"" + field.getName() + "\": [";
                    for (int i = 0; i < ids.size(); i++) {
                        json += ids.get(i) + (i + 1 < ids.size() ? ", " : "");
                    }
                    json += "],\n";
                } else if (field.getType().equals(MappedField.class)) {
                    json += "\t\"" + field.getName() + "\": " + MappedField.class.getMethod("getId").invoke(value) + ",\n";
                } else {
                    json += "\t\"" + field.getName() + "\": ";
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
        json = json.substring(0, json.length() - 2) + "\n}";
        return json;
    }
}

