package utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static utils.GuardSchedules.ScheduleEntry;

public class GuardSchedules implements Iterable<ScheduleEntry> {
    List<ScheduleEntry> schedules = new ArrayList<>();

    public GuardSchedules(Path schedule) throws IOException {
        Files
                .lines(schedule)
                .forEach(entry -> {
                    schedules.add(new ScheduleEntry(entry));
                });

        schedules.sort(Comparator.comparing(a -> a.timestamp));
    }

    public List<ScheduleEntry> getGuardStarts() {
        List<ScheduleEntry> starts = new ArrayList<>();

        schedules.forEach(e -> {
            if (e.action.startsWith("Guard")) {
                starts.add(e);
            }
        });

        return starts;
    }

    public void printAllForId(String id){
        schedules
                .stream()
                .filter(s -> s.action.contains(id))
                .forEach(System.out::println);
    }

    public List<ScheduleEntry> getScheduleFor(LocalDate date) {
        return schedules
                .stream()
                .filter(s -> s.timestamp.toLocalDate().isEqual(date))
                .filter(s -> s.action.contains("wakes") || s.action.contains("falls"))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Schedules: \n" +
                schedules.stream()
                        .map(ScheduleEntry::toString)
                        .collect(Collectors.joining());
    }


    @Override
    public Iterator<ScheduleEntry> iterator() {
        return schedules.iterator();
    }

    @Override
    public void forEach(Consumer<? super ScheduleEntry> action) {
        schedules.forEach(action);
    }

    @Override
    public Spliterator<ScheduleEntry> spliterator() {
        return schedules.spliterator();
    }

    public void printAllForDay(LocalDate of) {
        schedules
                .stream()
                .filter(s -> s.timestamp.toLocalDate().isEqual(of))
                .forEach(System.out::println);
    }

    public static class ScheduleEntry {
        LocalDateTime timestamp;
        String action;

        ScheduleEntry(String entry) {
            String datePart = entry.split("] ")[0].substring(1);
            this.timestamp = LocalDateTime.parse(datePart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.action = entry.split("]")[1].trim();
        }

        @Override
        public String toString() {
            return timestamp.toString() + ": " + action + "\n";
        }

        public String getGuardId() {
            if (action.startsWith("Guard")) {
                return "#" + action.split("#")[1].split(" ")[0];
            } else {
                return "";
            }
        }
    }
}
