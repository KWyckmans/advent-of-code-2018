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
        schedules.forEach(System.out::println);
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

    public List<ScheduleEntry> getScheduleFor(LocalDate date, Guard guard) {
        ScheduleEntry start = schedules.stream()
                .filter(s -> s.timestamp.toLocalDate().isEqual(date))
                .filter(s -> s.action.startsWith("Guard " + guard.getId() ))
                .collect(Collectors.toList()).get(0);

        int startIndex = schedules.indexOf(start);
        boolean endFound = false;
        int endIndex = startIndex + 1;

        while(!endFound && endIndex < schedules.size()){
            ScheduleEntry next = schedules.get(endIndex);

            if(next.action.contains("Guard")){
                endFound = true;
            }

            endIndex++;
        }

        if(endIndex == schedules.size()) {
//            System.out.println("Schedule for " + guard + ": " + schedules.subList(startIndex + 1, endIndex));
            return schedules.subList(startIndex + 1, endIndex);
        } else {
//            System.out.println("Schedule for " + guard + ": " + schedules.subList(startIndex + 1, endIndex - 1));
            return schedules.subList(startIndex + 1, endIndex - 1);
        }
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
        public LocalDateTime timestamp;
        public String action;
        String original;

        public ScheduleEntry(String entry) {
            this.original = entry;
            String datePart = entry.split("] ")[0].substring(1);
            this.timestamp = LocalDateTime.parse(datePart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.action = entry.split("]")[1].trim();
        }

        @Override
        public String toString() {
            return original;
        }

        public int getGuardId() {
            if (action.startsWith("Guard")) {
                return Integer.valueOf(action.split("#")[1].split(" ")[0]);
            } else {
                return -1;
            }
        }
    }
}
