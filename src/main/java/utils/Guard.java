package utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Guard {
    Map<Integer, Integer> sleepPattern = new HashMap<>();
    int totalMinutesAsleep;
    String id;

    public Guard(String id) {
        this.id = id;
    }

    public void fallAsleep(LocalDateTime time) {
        System.out.println("Guard " + id + " falling asleep at " + time);
    }

    public void wakeUp(LocalDateTime time) {
        System.out.println("Guard " + id + " waking up at " + time);
    }
}
