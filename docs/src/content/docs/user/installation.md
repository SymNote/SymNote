---
title: Installation & Usage
description: How to install, build, and run SymNote programs.
sidebar:
  order: 2
---

SymNote is a custom interpreted language built on Java and ANTLR v4.

## Requirements

- **Java Development Kit (JDK) 11** or higher
- A Bash-compatible terminal (Linux, macOS, or WSL on Windows)
- All ANTLR runtime dependencies are already included in the `lib/` directory

## Step 1 – Generate the Lexer and Parser

Before anything else, run the `generate.sh` script. It compiles the ANTLR grammar (`grammar/SymNote.g4`) into Java source files, which are placed in `src/gen/`.

```bash
./generate.sh
```

You only need to do this once, or whenever the grammar file changes.

## Step 2 – Run a Script

Use the `run.sh` script to compile all Java sources and execute a `.symnote` file:

```bash
./run.sh examples/HelloWorld.symnote
```

This compiles the Java sources (including generated ANTLR files) on the fly and passes the given `.symnote` file to the interpreter. The audio output is rendered via Java's built-in MIDI synthesizer.

## Running Tests

The project includes a JUnit test suite. To execute all tests:

```bash
./test.sh
```

Results are printed to the console and saved in `logs/test_results.log`.

## Writing Your First Program

Create a file with the `.symnote` extension. A minimal program that plays a C major arpeggio looks like this:

```ts
set_bpm(120);

synth Piano = load_synth("piano");

track Arpeggio(int bars) {
    use_synth(Piano);
    loop (int i from 1 to bars) {
        grid(1/8) {
            C4 E4 G4 C5 G4 E4 C4 -
        }
    }
}

Arpeggio(2);
```

Run it with:

```bash
./run.sh my_song.symnote
```
