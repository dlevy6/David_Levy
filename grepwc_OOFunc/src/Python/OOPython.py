import re
import sys


class FilterLines:
    def __init__(self, str):
        self.str = str

    def process(self, fileName):
        f = open(fileName, "r")
        lines = f.readlines()
        f.close()
        str2 = ""
        for line in lines:
            if not line.find(self.str) == -1:
                str2 += line
        return str2


class ConvertCase:

    def process(string):
        return string.lower()


class FindWords:

    def process(str):
        return str.split()


class NonAlphabeticFilter:

    def process(lst):
        for w in range(len(lst)):
            lst[w] = lst[w].replace("\\n", "")
            lst[w] = re.sub(r'[^a-zA-Z ]', '', str(lst[w]))


        return lst


class CountWords:

    def process(lst):
        count = {}
        for word in lst:
            if word in count:
                count[word] += 1
            else:
                count[word] = 1
        return count

class DocumentProcessor:
    def __init__(self, builder):
        self.builder = builder
        self.filteredLines = self.builder.lineFilter
        self.convertCase = self.builder.convertCase
        self.findWords = self.builder.findWords
        self.nonABC = self.builder.nonAlphabetic
        self.wordCount = self.builder.countWords

    def process(self, filename):
        #grep
        if self.convertCase is None:
            return {self.filteredLines.process(filename) : 1}

        elif self.filteredLines is None:
            f = open(filename, "r")
            lines = str(f.readlines())
            first = ConvertCase.process(lines)
            sec = FindWords.process(first)
            thir = NonAlphabeticFilter.process(sec)
            return CountWords.process(thir)
        else:
            a = self.filteredLines.process(filename)
            first = ConvertCase.process(a)
            sec = FindWords.process(first)
            thir = NonAlphabeticFilter.process(sec)
            return CountWords.process(thir)


    class Builder:
        def __init__(self):
            self.lineFilter = None
            self.convertCase = None
            self.findWords = None
            self.nonAlphabetic = None
            self.countWords = None

        def setLineFilter(self, lf):
            self.lineFilter = lf
            return self

        def setConvertCase(self, cc):
            self.convertCase = ConvertCase()
            return self

        def setFindWords(self, fw):
            self.findWords = fw
            return self

        def setNonAlphabetic(self, na):
            self.nonAlphabetic = na
            return self

        def setCountWords(self, cw):
            self.countWords = cw
            return self

        def build(self):
            return DocumentProcessor(self)

class Driver:
    def __init__(self, aug):
        lf = FilterLines(aug[2])
        caseconvert = ConvertCase()
        findwords = FindWords()
        nonabc = NonAlphabeticFilter()
        wordcount = CountWords()

        if len(aug) > 4:
            hm = DocumentProcessor.Builder().setLineFilter(lf).setConvertCase(caseconvert).setFindWords(findwords) \
                .setNonAlphabetic(nonabc).setCountWords(wordcount).build().process(aug[3])
            for key, value in hm.items():
                print(key + " " + str(value))
        elif aug[1] == 'grep':
            hm = DocumentProcessor.Builder().setLineFilter(lf).build().process(aug[3])
            for key in hm.items():
                print(*key)

        elif aug[1] == 'wc':
            hm = DocumentProcessor.Builder().setConvertCase(caseconvert).setFindWords(findwords)\
                .setNonAlphabetic(nonabc).setCountWords(wordcount).build().process(aug[2])
            for key,value in hm.items():
                print(key + " " + str(value))

def main():
    grep = ["grep", "this", '/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt'];
    wc = ["wc", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"];
    both = ["grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt", "|", "wc"];
    Driver(sys.argv)

if __name__ == "__main__":
    main()


