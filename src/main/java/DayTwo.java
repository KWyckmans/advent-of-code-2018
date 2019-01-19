import utils.ChecksumCalculator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DayTwo {
    public static void main(String[] args) {

        try {
            Path file = Paths.get(DayOne.class.getResource("daytwo/input.txt").toURI());
            ChecksumCalculator calculator = new ChecksumCalculator(file);
            System.out.println(calculator.calculateChecksum());
        } catch (URISyntaxException e) {
            System.err.println("Could not find specified file");
        } catch (IOException e) {
            System.err.println("Could not read specified file   ");
        }
    }
}
