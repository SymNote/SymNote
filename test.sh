#!/bin/bash
# Compiles and runs the SymNote test suite using JUnit 5

set -e

ANTLR_JAR="lib/antlr-4.13.2-complete.jar"
JUNIT_JAR="lib/junit-platform-console-standalone-1.10.2.jar"

MAIN_SRC="src/gen/*.java src/audio/*.java src/environment/*.java src/midi/*.java src/*.java"
TEST_SRC="src/test/java/*.java"

LOG_DIR="logs"
mkdir -p "$LOG_DIR"
FULL_LOG="$LOG_DIR/test_results.log"

echo "=== Compiling main sources ==="
mkdir -p out/main
javac -cp "$ANTLR_JAR:src:src/gen" $MAIN_SRC -d out/main

echo "=== Compiling test sources ==="
mkdir -p out/test
javac -cp "$ANTLR_JAR:$JUNIT_JAR:out/main:src:src/gen" $TEST_SRC -d out/test

echo "=== Running tests ==="
set +e
set -o pipefail

java -jar "$JUNIT_JAR" \
    --class-path "$ANTLR_JAR:out/main:out/test" \
    --scan-class-path \
    --details=verbose 2>&1 | sed 's/ms)/ms)\n/' | tee "$FULL_LOG"

TEST_EXIT_CODE=$?
set +o pipefail
set -e

exit $TEST_EXIT_CODE
