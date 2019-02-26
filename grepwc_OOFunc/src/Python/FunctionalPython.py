import re
import sys
from functools import reduce


def driver(aug):

    if len(aug) > 4:
        f = open(aug[3], "r")
        content = [line for line in f]
        out = list(filter(lambda line: aug[2] in line, map(lambda line: line, content)))
        sec = list(map(lambda line: line.lower(), out))
        thir = list(map(lambda line: line.split(), sec))
        flatten = list(reduce(lambda x, y: x + y, thir))
        values = list(map(lambda word: re.sub(r'[^a-zA-Z ]', '', str(word)), flatten))
        five = dict((i, values.count(i)) for i in values)
        print("{" + "\n".join("{}: {}".format(k, v) for k, v in five.items()) + "}")

    elif aug[1] == 'grep':

        f = open(aug[3], "r")
        content = [line for line in f]
        out = list(filter(lambda line: aug[2] in line, map(lambda line: line, content)))
        print(out)

    elif aug[1] == 'wc':
        f = open(aug[2], "r")
        content = [line for line in f]
        sec = list(map(lambda line: line.lower(), content))
        thir = list(map(lambda line: line.split(), sec))
        flatten = list(reduce(lambda x,y: x+y,thir))
        values = list(map(lambda word: re.sub(r'[^a-zA-Z ]', '', str(word)), flatten))
        five = dict((i,values.count(i)) for i in values)
        six = list(map(lambda val: (val, [i for i in range(len(values)) if values[i] == val]), values))

        print("{" + "\n".join("{}: {}".format(k, v) for k, v in five.items()) + "}")
# Map < String, Integer > counts = Files.lines(Paths.get(aug[1]))
# .map(String::toLowerCase)
# .map(s -> s.split("\\s+")).flatMap(Arrays::stream)
# .map(strings -> strings.replaceAll("[^a-zA-Z]", ""))
# .map(word -> new
# AbstractMap.SimpleEntry <> (word, 1))
# .collect(toMap(e -> e.getKey(), e -> e.getValue(), (v1, v2) -> v1 + v2));
# counts.forEach((k, v) -> System.out.println(String.format("%s %d", k, v)));


def main():
    grep = ["grep", "this", '/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt']
    wc = ["wc", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"]
    both = ["grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt",
            "|", "wc"]
    driver(sys.argv)

if __name__ == "__main__":
    main()