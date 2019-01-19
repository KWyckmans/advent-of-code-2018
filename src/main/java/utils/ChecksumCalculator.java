package utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.lines;

public class ChecksumCalculator {
    private Path file;
    private Long twoChars;
    private Long threeChars;

    public ChecksumCalculator(Path file) {
        this.file = file;
    }

    private void load() throws IOException {
        System.out.println("Loading data from file");

        this.twoChars = lines(file)
                .filter(this::checkIfTwoCharacters)
                .count();


        this.threeChars = lines(file)
                .filter(this::checkIfThreeCharacters)
                .count();
    }

    public long calculateChecksum() throws IOException {
        if(twoChars == null) load();

        return twoChars * threeChars;
    }

    private Map<Character, Integer> charCounts(String line) {
        Map<Character, Integer> charCounts = new HashMap<>();

        for (char c : line.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        return charCounts;
    }

    private boolean checkIfTwoCharacters(String line) {
        Map<Character, Integer> counts = charCounts(line);
        return checkIfNCharacters(counts, 2);
    }

    private boolean checkIfThreeCharacters(String line) {
        Map<Character, Integer> counts = charCounts(line);
        return checkIfNCharacters(counts, 3);
    }

    private boolean checkIfNCharacters(Map<Character, Integer> counts, int n) {
        for (char c : counts.keySet()) {
            int count = counts.get(c);
            if (count == n) {
                return true;
            }
        }
        return false;
    }
}
