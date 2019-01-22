import com.sun.tools.javac.util.Pair;
import utils.GuardSchedules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DayFour {
    public static void main(String[] args) {
        try {
            Path file = Paths.get(DayFour.class.getResource("dayfour.txt").toURI());

            List<GuardSchedules.ScheduleEntry> schedule =
                    Files.lines(file)
                            .map(GuardSchedules.ScheduleEntry::new)
                            .sorted(Comparator.comparing(a -> a.timestamp))
                            .collect(Collectors.toList());

            int guard = -1;
            LocalDateTime asleep = null;

            Map<Pair<Integer, Integer>, Integer> CM = new HashMap<>();
            Map<Integer, Integer> C = new HashMap<>();

            for (GuardSchedules.ScheduleEntry entry : schedule) {
                if (entry.action.startsWith("Guard")) {
                    guard = entry.getGuardId();
                } else if (entry.action.startsWith("falls asleep")) {
                    asleep = entry.timestamp;
                } else {
                    for (int minute = asleep.getMinute(); minute < entry.timestamp.getMinute(); minute++) {
                        CM.put(Pair.of(guard, minute), CM.getOrDefault(Pair.of(guard, minute), 0) + 1);
                        C.put(guard, C.getOrDefault(guard, 0) + 1);
                    }
                }
            }

            Optional<Integer> maxSleepingGuard = C.keySet().stream().max(Comparator.comparing(C::get));


            Optional<Pair<Integer, Integer>> maxMinute = CM.keySet()
                    .stream()
                    .filter(k -> k.fst.equals(maxSleepingGuard.get()))
                    .max(Comparator.comparing(CM::get));

            System.out.println("Sleepiest minute (CM): " + maxMinute.get());
            System.out.println("Result: " + (maxMinute.get().snd * maxSleepingGuard.get()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
