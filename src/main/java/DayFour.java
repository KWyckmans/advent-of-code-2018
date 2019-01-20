import utils.GuardSchedules;
import utils.ScheduleAnalyser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DayFour {
    public static void main(String[] args) {
        try {
            Path file = Paths.get(DayFour.class.getResource("dayfour.txt").toURI());
            GuardSchedules schedule = new GuardSchedules(file);
            ScheduleAnalyser analyser = new ScheduleAnalyser(schedule);
            analyser.analyse();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
