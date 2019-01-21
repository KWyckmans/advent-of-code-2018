import utils.Guard;
import utils.GuardSchedules;
import utils.ScheduleAnalyser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class DayFour {
    public static void main(String[] args) {
        try {
            Path file = Paths.get(DayFour.class.getResource("dayfour.txt").toURI());

            GuardSchedules schedule = new GuardSchedules(file);
            ScheduleAnalyser analyser = new ScheduleAnalyser(schedule);
            analyser.analyse();

            Optional<Guard> sleepy = analyser.getMostSleepingGuard();
            int sleepiestMinute = sleepy.get().getSleepiestMinute();

            System.out.println("The sleepiest guard is " +  sleepy.get() +". He was sleeping the most on minute: " + sleepiestMinute + ". He slept for a total of " + sleepy.get().totalMinutesAsleep + " minutes.");
            System.out.println("Result is " + sleepy.get().getId() + " x " + sleepy.get().getSleepiestMinute());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
