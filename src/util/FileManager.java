package util;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static <T> void save(String fileName, ArrayList<T> data) {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> load(String fileName) {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (ArrayList<T>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
