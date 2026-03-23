---
title: Language Principles
description: The core execution model, AST architecture, and deterministic timing of the SymNote engine.
---

SymNote is not just a text parser; it is a specialized compiler designed to bridge the gap between structural programming and sample-accurate audio generation. 

This document defines the high-level logic and architectural philosophy that makes SymNote tick.

---

## 1. Execution Model: AST-Based Interpretation

To process `.symnote` files, the engine utilizes a multi-stage interpretation pipeline powered by **ANTLR4** (ANother Tool for Language Recognition). The journey from raw text to audible music happens in three distinct phases:

### Phase A: Lexical & Syntactic Analysis
The raw source code is fed into the ANTLR4 Lexer, which breaks the text into individual tokens (keywords, numbers, notes). The Parser then validates these tokens against the `SymNote.g4` grammar rules, organizing them into a **Concrete Parse Tree**. If there is a missing semicolon or an unmatched bracket, the process fails here.

### Phase B: AST Transformation
The Concrete Parse Tree contains a lot of grammatical "noise" (like parentheses and brackets). The engine strips this away, refining the data into an **Abstract Syntax Tree (AST)**. This tree is a simplified, highly optimized representation of the program's pure logic.

### Phase C: Visitor Pattern Evaluation
To execute the music, the SymNote interpreter traverses the AST using the **Visitor Pattern**. 
Unlike a standard *Listener* pattern (which blindly triggers events as it walks the tree), the Visitor pattern gives the engine **explicit, manual control** over the traversal. 

This is crucial for SymNote because:
* If an `if (false)` statement is encountered, the Visitor can actively choose *not* to visit that branch.
* If a `loop` is encountered, the Visitor can repeatedly visit the exact same AST subtree without having to re-parse the original text.
* It allows for safe, recursive calls inside `routine` definitions.

---

## 2. Deterministic Timing

General-purpose programming languages (like Python or standard Java) are at the mercy of the CPU scheduler. A loop might execute in 1 millisecond today, and 3 milliseconds tomorrow. In audio generation, this causes jitter, dropouts, and out-of-sync beats.

SymNote solves this using a **Deterministic Audio Clock**.

### BPM Dominance
All time advancements inside a `grid` block are calculated strictly relative to the engine's internal `BPM` setting (defined by `set_bpm()`). Time is calculated mathematically before the audio buffer is ever filled. 

For example, at 120 BPM, a `1/16` grid resolution mathematically guarantees that every step will advance the logical timeline by exactly 125 milliseconds.

### Decoupled Timeline (Logical vs. Real Time)
A standard programming language is at the mercy of CPU execution time. If a loop takes 5 milliseconds to compute, the entire program is delayed by 5 milliseconds. In audio generation, this causes jitter and out-of-sync beats.

SymNote solves this by completely decoupling **Execution Time (CPU)** from the **Logical Timeline (The Playhead)**.

When the SymNote interpreter evaluates programming logic (like variable assignments, `if` statements, `while` loops, or math `routine` calculations), the CPU is working, but the musical playhead remains strictly **frozen**. 

The logical timeline **only** advances when the engine explicitly evaluates a time-advancing token inside a `grid` (a Note, a Rest `-`, or a Sustain `~`). 

Because of this explicit time advancement:
1. **Sample-Accurate Polyphony:** When the engine evaluates `[C4, E4, G4]`, the CPU processes three separate notes, but all three are stamped onto the audio buffer at the exact same logical microsecond.
2. **Perfect Parallel Sync:** When a `parallel` block runs two separate `track` elements concurrently, the engine calculates their timelines independently. A track with heavy mathematical loops will take more *CPU time* to compile, but its *Logical Timeline* will never lag behind a simple drum track. They will sync perfectly when the audio is rendered.