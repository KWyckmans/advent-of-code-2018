package utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ScheduleAnalyser {
    private final GuardSchedules schedule;
    private Map<String, Guard> guards = new HashMap<>();

    public ScheduleAnalyser(GuardSchedules schedule) {
        this.schedule = schedule;
    }

    public Optional<Guard> getMostSleepingGuard(){
        return guards.values().stream().max(Comparator.comparingInt(g -> g.totalMinutesAsleep));
    }

    public void analyse() {
        List<GuardSchedules.ScheduleEntry> guardStarts = schedule.getGuardStarts();

        guardStarts.forEach(
                s -> {
                    String id = s.getGuardId();
                    Guard guard = guards.getOrDefault(id, new Guard(id));

                    System.out.println("Looking up schedule for " + guard + " on " + s.timestamp);
                    List<GuardSchedules.ScheduleEntry> guardSchedule = schedule.getScheduleFor(s.timestamp.toLocalDate(), guard);

                    guardSchedule.forEach(sched -> {
                        if (sched.action.startsWith("falls")) {
                            guard.fallAsleep(sched.timestamp);
                        } else if (sched.action.startsWith("wakes")) {
                            guard.wakeUp(sched.timestamp);
                        }
                    });

                    guards.put(id, guard);
                }
        );
    }
}
