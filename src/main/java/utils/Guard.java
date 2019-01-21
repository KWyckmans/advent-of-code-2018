package utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Guard {
    private Map<Integer, Integer> sleepPattern = new HashMap<>();
    public int totalMinutesAsleep;
    private String id;

    private boolean isAsleep;
    private int lastFellAsleepAt;

    public Guard(String id) {
        this.id = id;
    }

    public void fallAsleep(LocalDateTime time) {
//        System.out.println("Guard " + id + " falling asleep at " + time);
        isAsleep = true;
        lastFellAsleepAt = time.getMinute();
    }

    public void wakeUp(LocalDateTime time) {
        isAsleep = false;
        int minutesAsleep = time.getMinute() - lastFellAsleepAt;
        totalMinutesAsleep += minutesAsleep;
//
//        if (id.equals("#449")) {
//            System.out.println("Guard " + id + " fell asleep at " + lastFellAsleepAt +" and woke up at " + time.getMinute() + ". Slept for " + minutesAsleep + " mins. Current sleep total is " + totalMinutesAsleep + " minutes");
//        }

        markPeriodAsSleeping(lastFellAsleepAt, time.getMinute());
    }

    private void markPeriodAsSleeping(int start, int end) {
        for (int i = start; i < end; i++) {
            int minutesSleeping = sleepPattern.getOrDefault(i, 0) + 1;
            sleepPattern.put(i, minutesSleeping);

//            if (id.equals("#449")){
//                System.out.println("Incrementing minute " + i);
//            }
        }
    }

    public int getSleepiestMinute() {
        int maxMinutesSlept = 0;
        int maxKey = 0;

        for (int key : sleepPattern.keySet()) {
            int minutes = sleepPattern.get(key);
//            System.out.println("minute " + key + " has " + minutes + " minutes of sleep. Current max is " + maxMinutesSlept);
            if (maxMinutesSlept < minutes) {
//                System.out.println("Swapping values");
                maxKey = key;
                maxMinutesSlept = minutes;
            }
        }

        return maxKey;
    }

    @Override
    public String toString() {
        return "Guard (" + id + ")";
//                sleepPattern.keySet().stream().map( k -> "minute: " + k + " - value: " + sleepPattern.get(k) + '\n').collect(Collectors.joining());
    }

    public String getId() {
        return this.id;
    }
}
