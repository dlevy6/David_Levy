
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Driver {
    public Driver(String[] aug) throws IOException {

        if (aug.length > 3) {
            Map<String, Integer> counts = Files.lines(Paths.get(aug[2]))
                    .filter(line -> line.contains(aug[1]))
                    .map(String::toLowerCase)
                    .map(s -> s.split("\\s+")).flatMap(Arrays::stream)
                    .map(strings -> strings.replaceAll("[^a-zA-Z]", ""))
                    .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (v1, v2) -> v1 + v2));
            counts.forEach((k, v) -> System.out.println(String.format("%s %d", k, v)));



        } else if (aug[0].equals("grep")) {

            Files.lines(Paths.get(aug[2]))
                    .filter(line -> line.contains(aug[1]))
                    .collect(Collectors.toList())
                    .forEach(System.out::println);




        } else if (aug[0].equals("wc")) {


            Map<String, Integer> counts = Files.lines(Paths.get(aug[1]))
                    .map(String::toLowerCase)
                    .map(s -> s.split("\\s+")).flatMap(Arrays::stream)
                    .map(strings -> strings.replaceAll("[^a-zA-Z]", ""))
                    .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (v1, v2) -> v1 + v2));
            counts.forEach((k, v) -> System.out.println(String.format("%s %d", k, v)));


                    //
                    //collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            //System.out.println(counted);
            //.collect(toMap(Function.identity(), String::length))
        }
    }


    public static void main(String[] args) {
        String[] ms = {"grep",  "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"};
        String[] wc = {"wc", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"};
        String[] both = {"grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt", "|", "wc"};
        try {
            Driver md = new Driver(args);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

