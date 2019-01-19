import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


public class DayOne {
    public static void main(String[] args) {
        try {
            Path file = Paths.get(DayOne.class.getResource("dayone/input.txt").toURI());
            int sum = Files.lines(file).mapToInt(Integer::valueOf).sum();
            System.out.println("The total of all frequencies is: " + sum);

            int[] nums = Files.lines(file).mapToInt(Integer::valueOf).toArray();
            Set<Integer> subTotals = new HashSet<>();
            int prevTotal = 0;
            subTotals.add(prevTotal);

            for (int i = 0; i < nums.length; i++) {
                int newTotal = prevTotal + nums[i];

                if (subTotals.contains(newTotal)) {
                    System.out.println("Encountered first double total: " + newTotal);
                    return;
                }

                subTotals.add(newTotal);
                prevTotal = newTotal;

                if(i + 1 == nums.length){
                    i = -1;
                }
            }

        } catch (IOException | URISyntaxException e) {
            System.err.println("Could not read input file");
        }
    }
}
