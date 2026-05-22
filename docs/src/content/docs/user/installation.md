---
title: Installation & Usage
description: How to install, build, and run SymNote programs.
sidebar:
  order: 2
---

SymNote is a custom interpreter built using Java and ANTLR.

## Requirements

- **Java Development Kit (JDK) 11** or higher.
- Bash terminal (Linux/macOS or WSL on Windows).
- All necessary dependencies (like ANTLR) are already included in the `lib/` directory.

## Compilation

Before running any script, you must generate the lexer and parser using the ANTLR grammar, and then compile the Java source files.

1. **Generate the Lexer and Parser:**
   Run the following script to generate the necessary Java files from `grammar/SymNote.g4`:
   ```bash
   ./generate.sh
   ```

2. **Compile and Run:**
   The `run.sh` script automatically compiles all the Java code and executes the given script.

## Running a Script

To execute a `.symnote` script, use the `run.sh` bash script and provide the path to your script:

```bash
./run.sh examples/HelloWorld.symnote
```

## Running Tests

To run the automated JUnit test suite for the interpreter:

```bash
./test.sh
```
The test results will be printed to the console and saved in the `logs/test_results.log` file.
