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
        guards.values().stream().forEach(g -> System.out.println(g.getId() + ": " + g.totalMinutesAsleep));
        return guards.values().stream().max(Comparator.comparingInt(g -> g.totalMinutesAsleep));
    }

    public void analyse() {
        List<GuardSchedules.ScheduleEntry> guardStarts = schedule.getGuardStarts();

        guardStarts.forEach(
                s -> {
                    String id = String.valueOf(s.getGuardId());
                    Guard guard = guards.getOrDefault(id, new Guard(id));

                    List<GuardSchedules.ScheduleEntry> guardSchedule = schedule.getScheduleFor(s.timestamp.toLocalDate(), guard);

//                    if(guard.getId().equals("#449")){
//                        System.out.println(guardSchedule);
//                    }

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
