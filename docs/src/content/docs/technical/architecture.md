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

SymNote uses its own custom call stack stored in memory (the heap) rather than relying on Java's built-in call stack. This design provides safe, precise control over execution flow and prevents severe crashes.

The mechanism relies on two main components:
1. **`ActivationRecord`**: Represents a single function call (an execution frame). It holds the routine's name and its local variables (`Environment`).
2. **`CallStack`**: Manages these records using a double-ended queue (`ArrayDeque`).

#### 1. Recursion Limit Protection
The custom `CallStack` enforces a strict maximum depth (`MAX_DEPTH = 200`). If a user writes an infinite loop using recursion, Java will not crash with a native `java.lang.StackOverflowError`. Instead, SymNote safely catches it and throws a clear runtime error.

**Example (SymNote Code):**
```ts
routine infiniteLoop() returns void {
    infiniteLoop();
}
infiniteLoop();
```
**Resulting Error:**
SymNote StackOverflow: Maximum recursion depth of 200 exceeded at line 2

#### 2. Detailed Error Tracing
Because every ActivationRecord tracks its context, the interpreter can generate a clear stack trace if a runtime error occurs deep inside nested routines. This makes debugging much easier for the user.

#### 3. Execution Lifecycle (Under the Hood)
In the Java backend, the interpreter safely manages the lifecycle of each frame using try-finally blocks. This guarantees the stack remains perfectly consistent, even if a routine uses an early return or throws an exception.

Example (Java Implementation):
```java
// 1. Create a new environment for the routine
Environment routineEnv = new Environment(interpreter.globalEnv);

// 2. Create and push the activation record onto the custom stack
ActivationRecord frame = new ActivationRecord(functionName, routineEnv);
interpreter.callStack.push(frame, ctx.getStart().getLine());

// Switch to the routine's context
Environment callerEnv = interpreter.env;
interpreter.env = routineEnv;

try {
    // 3. Execute the routine block
    interpreter.visit(routineCtx.blockRoutine());
} finally {
    // 4. Safely pop the frame and restore the caller's environment
    interpreter.callStack.pop();
    interpreter.env = callerEnv;
}
```

### Lexical Scoping and Environment Chaining
Block-level scopes (used in `if`, `while`, `loop`, and nested `{}`) are managed via an `Environment` chain. Each new scope holds a pointer (`parent`) to its enclosing scope.
- **Entering/Exiting Scopes:** Handled explicitly by `enterScope()` and `exitScope()` to prevent memory leaks and ensure tight lifecycle control.
- **Parent Keyword Resolution:** The `parent::` keyword traverses this pointer chain iteratively ($O(N)$ depth). This allows precise variable shadowing resolution and assignments without any recursive overhead on the Java stack.

### Grid Memory Safety

The Grid block syntax uses a flat list (`gridSymbol+`) instead of recursion. Because of this, very long songs with thousands of musical notes or rests will not crash the Java memory. 

The interpreter processes the grid symbols using a standard `for` loop. This completely removes the risk of a native `java.lang.StackOverflowError` when reading large musical files.
