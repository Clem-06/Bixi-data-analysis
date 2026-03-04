package ca.concordia.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class myDictionary {

    private int[] ids;
    private String[] words;
    private int size;

    public myDictionary(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine();

            if (line == null || line.isEmpty()) {
                throw new IllegalArgumentException("File is empty.");
            }

            String[] input = line.split(",");

            ids = new int[input.length];
            words = new String[input.length];

            for (int i = 0; i < input.length; i++) {
                ids[i] = i;
                words[i] = input[i].trim();
            }
            size = input.length;

        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file");
        }
    }

    public String getWord(int id) {
        if (id >= 0 && id < size)
            return words[id];
        return null;
    }

    public int getId(String word) {
        for (int i = 0; i < size; i++) {
            if (words[i].equals(word))
                return i; //Both arrays are indexed aligned
        }
        return -1;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.println(ids[i] + " -> " + words[i]);
        }
    }

    public int getSize() {
        return size;
    }
}