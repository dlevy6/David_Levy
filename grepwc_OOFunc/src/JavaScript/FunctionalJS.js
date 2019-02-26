
class Driver{
    constructor(aug){

        if (aug.length > 5){
            let output = require('fs')
                .readFileSync(aug[4], 'utf8').split("\n")
                .filter(line => line.includes(aug[3]))
                .map( line => line.toLowerCase())
                .map( line => line.trim().split(/\s+/))
                .reduce( (x,y) => x.concat(y))
                .map( word => word.replace(/[^0-9a-zA-Z]/g, ''))
                .reduce((prev, curr) => (prev[curr] = ++prev[curr] || 1, prev), {});

            console.log(output)
        }
        else if (aug[2] == "grep"){

            let output = require('fs')
                .readFileSync(aug[4], 'utf8').split("\n")
                .filter(line => line.includes(aug[3]));

            console.log(output)
        }
        else if (aug[2] == "wc"){
            let out = require('fs')
                .readFileSync(aug[3], 'utf8').split("\n")
                .map( line => line.toLowerCase())
                .map( line => line.trim().split(/\s+/))
                .reduce( (x,y) => x.concat(y))
                .map( word => word.replace(/[^0-9a-zA-Z]/g, ''))
                .reduce((prev, curr) => (prev[curr] = ++prev[curr] || 1, prev), {});

            console.log(out)


        }
    }
}

let grep = ["grep", "I", '/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt'];
let wc = ["wc", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"];
let both = ["grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt",
    "|", "wc"];
const args = process.argv;
new Driver(args);