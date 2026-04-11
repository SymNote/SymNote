#!/bin/bash
# Compiles the SymNote grammar using ANTLR

JAR_PATH="lib/antlr-4.13.2-complete.jar"
if [ ! -f "$JAR_PATH" ]; then
    echo "Error: ANTLR jar not found at $JAR_PATH"
    exit 1
fi

echo "Generating parser with ANTLR..."
mkdir -p src/gen

cd grammar || exit
java -jar "../$JAR_PATH" -visitor -package gen -o ../src/gen SymNote.g4
cd ..

echo "Done. Parser files are in src/gen/"
