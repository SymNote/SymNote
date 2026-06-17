---
title: System Architecture
description: Overview of the SymNote architecture and pipeline.
sidebar:
  order: 1
---

SymNote is designed around a strictly phased execution pipeline that completely decouples language logic from audio rendering. 

## The System Pipeline

The compilation and execution of a `.symnote` file follow a three-step pipeline:

1. **Frontend (ANTLR4)**: The source text is converted into tokens by the Lexer, and structured into a Parse Tree by the Parser. Syntax errors are caught here.
2. **Interpreter (AST Visitor)**: The `SymNoteInterpreter` traverses the Parse Tree. It executes logic, resolves variables, performs type-checking, manages scoping, and expands loops/routines.
3. **Backend (Renderer)**: Once the Interpreter successfully maps all execution to time-based events on the `SymNoteTimeline`, the AST traversal stops. The `JavaMidiRenderer` then consumes this timeline to produce synchronized audio playback.

## The 3-Level Constraint Architecture

The most distinctive architectural feature of SymNote is its **3-Level Constraint System**.

1. **Level 1 (Global Scope)**: Global definitions (`set_bpm`, global variables, `track`, `routine`). Defines the structure of the program.
2. **Level 2 (Track Body)**: Represents an isolated execution thread for a specific instrument (`use_synth`). Contains logic (`if`, `loop`, `while`) but no timeline manipulation other than advancing time through grids.
3. **Level 3 (Grid)**: The lowest level. A pure time-domain environment mapping musical symbols to specific ticks. No logical constructs or variable assignments are permitted here.

This architecture ensures that time moves predictably. By forcing all logic into Level 2 and all time-advancement into Level 3, the interpreter can map complex, concurrent (`parallel`) instructions to a linear timeline without race conditions or complex runtime threading.

## Memory Management and Scope Resolution

A standard Tree-Walk interpreter in Java often suffers from a critical architectural flaw: it relies heavily on the JVM's call stack to manage target-language recursion. This can quickly lead to unhandled `java.lang.StackOverflowError` crashes during deep recursion tests. 

To resolve this and ensure system stability, SymNote implements its own decoupled memory management architecture:

### Independent Call Stack (`CallStack` & `ActivationRecord`)
Routine and track calls do not use Java's local variables to track state. Instead, every function call generates an `ActivationRecord` placed on a custom `CallStack` that lives safely on the JVM Heap. 
- We enforce a strict, safe recursion limit (`MAX_DEPTH = 200`).
- If a script encounters infinite recursion, the interpreter securely catches it and throws a domain-specific `SymNote StackOverflow` error with the exact line number, gracefully preventing a JVM crash.

### Lexical Scoping and Environment Chaining
Block-level scopes (used in `if`, `while`, `loop`, and nested `{}`) are managed via an `Environment` chain. Each new scope holds a pointer (`parent`) to its enclosing scope.
- **Entering/Exiting Scopes:** Handled explicitly by `enterScope()` and `exitScope()` to prevent memory leaks and ensure tight lifecycle control.
- **Parent Keyword Resolution:** The `parent::` keyword traverses this pointer chain iteratively ($O(N)$ depth). This allows precise variable shadowing resolution and assignments without any recursive overhead on the Java stack.
