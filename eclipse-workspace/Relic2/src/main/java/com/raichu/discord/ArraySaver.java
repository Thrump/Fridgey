package com.raichu.discord;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ArraySaver {
    /**
     * Saves an array to a file
     * @param file the file to save to (erases any content in file)
     * @param array the array to save
     * @throws IOException if something goes wrong
     * @see File
     */
    public static void save(File file, String[] array) throws IOException {
        if (!file.exists() && file.createNewFile()) throw new IOException();
        PrintWriter printWriter = new PrintWriter(file);
        for (String string : array) {
            printWriter.println(string);
        }
        printWriter.close();
    }

    /**
     * Saves an array to a file
     * @param file the file to save to (erases any content in file)
     * @param list the array to save
     * @throws IOException if something goes wrong
     * @see File
     * @see List
     */
    public static void save(File file, List<String> list) throws IOException {
        save(file, list.toArray(new String[list.size()]));
    }

    /**
     * Loads a file to an array
     * @param file the file to read
     * @return an array of strings
     * @throws IOException if something goes wrong
     */
    public static String[] load(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        return lines.toArray(new String[lines.size()]);
    }
}