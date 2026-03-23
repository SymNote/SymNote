#!/bin/bash
# Compiles and runs a SymNote script

if [ -z "$1" ]; then
    echo "Usage: ./run.sh <path/to/script.symnote>"
    exit 1
fi

JAR_PATH="lib/antlr-4.13.2-complete.jar"

echo "Compiling java files..."
mkdir -p out
javac -cp "$JAR_PATH:src:src/gen" src/gen/*.java src/*.java -d out/
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo "Running script: $1"
echo "-----------------------------------"
java -cp "$JAR_PATH:out" Main "$1"
