class FilterLines{

    constructor(str) {
        this.str = str.toLowerCase();
    }
    process(fileName) {
        let fs = require('fs');
        let str2 = "";
        let contents = fs.readFileSync(fileName, 'utf8');
        for (let string of contents.split("\n")) {
            if (string.includes(this.str)) {
                str2 += string + "\n";
            }
        }

        return str2;
    }

}
class ConvertCase {

    process(str){
        return str.toLowerCase()
    }
}

class FindWords {

    process(str){
        return str.trim().split(/\s+/);
    }

}

class NonAlphabeticFilter {

    process(str){
        for (let i = 0; i<str.length; i++) {
            str[i] = str[i].replace(/[^0-9a-zA-Z]/g, '');

        }
        return str

    }
}

class CountWords{

    process(lst){
        let counts = {};
        for (let i = 0; i < lst.length; i++) {
            counts[lst[i]] = 1 + (counts[lst[i]] || 0);
        }
        return counts;
    }
}



class DocumentProcessorBuilder {
    constructor(builder) {
        this.filterdLines = builder.filterdLines;
        this.CaseConvert = builder.CaseConvert;
        this.wordFind = builder.wordFind;
        this.nonABC = builder.nonABC;
        this.wordCount = builder.wordCount;

    }

    process(fileName){
    //grep
    if(this.CaseConvert == null){
        let fn = this.filterdLines.process(fileName);
        return fn;
    }//wc
    else if(this.filterdLines == null){
        let fs = require('fs');
        let contents = fs.readFileSync(fileName, 'utf8');
        let first=  this.CaseConvert.process(contents);
        let sec = this.wordFind.process(first);
        let thir = this.nonABC.process(sec);
        return this.wordCount.process(thir);
    }
    else {
        let fileN = this.filterdLines.process(fileName);
        let first=  this.CaseConvert.process(fileN);
        let sec = this.wordFind.process(first);
        let thir = this.nonABC.process(sec);
        return this.wordCount.process(thir);
    }

    }
        static get Builder() {
            class Builder {
                setLineFilter(lf) {
                    this.filterdLines = lf;
                    return this;
                }

                setCaseConvert(CC) {
                    this.CaseConvert = CC;
                    return this;

                }

                setWordFinder(wf) {
                    this.wordFind = wf;
                    return this;
                }

                setNonABCFilter(nonABC) {
                    this.nonABC = nonABC;
                    return this;
                }

                setWordCounter(wc) {
                    this.wordCount = wc;
                    return this;
                }

                build() {
                    return new DocumentProcessorBuilder(this);
                }
            }
            return Builder;
        }
}

class Driver {
    constructor(aug) {
        let lf = new FilterLines(aug[3]);
        let caseConvert = new ConvertCase();
        let wordFind = new FindWords();
        let nonABC = new NonAlphabeticFilter();
        let wordCount = new CountWords();

        if (aug.length > 5) {

            let hm = new DocumentProcessorBuilder.Builder().setLineFilter(lf).setCaseConvert(caseConvert).setWordFinder(wordFind)
                .setNonABCFilter(nonABC).setWordCounter(wordCount).build().process(aug[4]);
            console.log(hm);

        } else if (aug[2] == "grep") {

            let hm = new DocumentProcessorBuilder.Builder().setLineFilter(lf).build().process(aug[4]);
            console.log(hm);

        } else if (aug[2]== "wc") {

            let hm = new DocumentProcessorBuilder.Builder().setCaseConvert(caseConvert).setWordFinder(wordFind)
                .setNonABCFilter(nonABC).setWordCounter(wordCount).build().process(aug[3]);
            console.log(hm);
        }
    }
}



let grep= ["grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"];
let wc = ["wc", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"];
let both = ["grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt", "|", "wc"];
const args = process.argv;
let tst = new Driver(args);

