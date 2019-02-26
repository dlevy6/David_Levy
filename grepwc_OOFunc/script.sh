#!/bin/bash

echo "input your command aruements:"
read command
echo "executing $command"
echo ""


echo "Java Object Oriented"
javac src/Java/OOJava/*.java
java -cp "src/Java/OOJava/" Driver $command
echo ""

echo "Java Functional"
javac src/Java/FunctionalJava/*.java
java -cp src/Java/FunctionalJava/ Driver $command
echo ""

echo "JavaScript Object Oriented"
node src/JavaScript/OOJS.js $command
echo ""

echo "JavaScript Functional"
node src/JavaScript/FunctionalJS.js $command
echo ""

echo "Python Object Oriented"
Python3 src/Python/OOPython.py $command
echo ""

echo "Python Functional"
Python3 src/Python/FunctionalPython.py $command
echo ""

echo "C Imperitive"
cd src/C
gcc ImpC.c -w
./a.out $command
cd ../..
echo ""

echo ""
echo "end of output"



