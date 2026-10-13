import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

public class Main {
    record Learner(String name, int mark) {}
    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("lab10");

        // Ex1: save five lines then read back
        Scanner in = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            System.out.print("Line " + i + ": ");
            lines.add(in.nextLine());
        }
        Path f = dir.resolve("notes.txt");
        Files.write(f, lines);
        System.out.println("Read back: " + Files.readAllLines(f));

        // Ex2: removeIf with Predicate
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        Predicate<Integer> even = n -> n % 2 == 0;
        nums.removeIf(even);
        System.out.println(nums);

        // Ex3: lines, words, characters
        long lineCount = lines.size();
        long wordCount = lines.stream().flatMap(l -> Arrays.stream(l.trim().split("\\s+"))).filter(w -> !w.isEmpty()).count();
        long charCount = Files.readString(f).length();
        System.out.println("lines=" + lineCount + " words=" + wordCount + " chars=" + charCount);

        // Ex4: above-average names sorted
        List<Learner> ls = List.of(new Learner("Tino", 80), new Learner("Amai", 55), new Learner("Rudo", 90), new Learner("Farai", 40));
        double avg = ls.stream().mapToInt(Learner::mark).average().orElse(0);
        System.out.println("avg=" + avg + " above: " + ls.stream().filter(l -> l.mark() > avg).map(Learner::name).sorted().collect(Collectors.toList()));

        // Ex5: CSV -> groupingBy band -> one file per band
        Path csv = dir.resolve("learners.csv");
        Files.write(csv, List.of("name,mark", "Tino,80", "Amai,55", "Rudo,90", "Farai,40", "Chipo,72", "Tawa,65"));
        List<Learner> loaded = Files.readAllLines(csv).stream().skip(1)
                .map(l -> l.split(",")).map(p -> new Learner(p[0], Integer.parseInt(p[1]))).toList();
        Map<String, List<Learner>> byBand = loaded.stream().collect(Collectors.groupingBy(l -> l.mark() >= 75 ? "A" : l.mark() >= 60 ? "B" : "C"));
        for (var e : new TreeMap<>(byBand).entrySet()) {
            Path report = dir.resolve("band_" + e.getKey() + ".txt");
            Files.write(report, e.getValue().stream().map(l -> l.name() + "," + l.mark()).toList());
            System.out.println(report.getFileName() + " -> " + Files.readAllLines(report));
        }
    }
}
