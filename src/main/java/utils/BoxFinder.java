package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BoxFinder {
    private final Path file;

    public BoxFinder(Path file) {
        this.file = file;
    }

    public String getBox() throws IOException {
        System.out.println("Loading data from file");

        List<String> entries = Files.readAllLines(file);

        for (int i = 0; i < entries.size() - 1; i++) {
            for (int j = i; j < entries.size(); j++) {
                int difference = countDifferences(entries.get(i), entries.get(j));

                if (difference != -1) {
                    return entries.get(i).substring(0, difference) + entries.get(i).substring(difference + 1);
                }
            }
        }

        return "";
    }

    private int countDifferences(String a, String b) {
        boolean differenceFound = false;
        int foundAt = -1;

        for (int i = 0; i < a.length(); i++) {
            char first = a.charAt(i);
            char second = b.charAt(i);

            if (first != second && !differenceFound) {
                differenceFound = true;
                foundAt = i;
            } else if (first != second) {
                return -1;
            }
        }

        if (differenceFound) return foundAt;

        return -1;
    }

}
