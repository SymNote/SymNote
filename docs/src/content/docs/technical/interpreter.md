---
title: Interpreter & Environment
description: How the SymNote Interpreter executes code, manages memory, and handles scopes.
sidebar:
  order: 3
---

The SymNote Interpreter is implemented as a Tree Visitor extending `SymNoteBaseVisitor<Object>`. It traverses the ANTLR-generated Parse Tree and evaluates it dynamically.

## The Visitor Pattern

Every node in the Parse Tree corresponds to a `visit[NodeName]` method in `SymNoteInterpreter.java`. 

For example, when evaluating an addition expression (`a + b`), the visitor visits the left child, visits the right child, performs the addition in Java, and returns the result up the call stack.

To handle complex sub-components, the interpreter delegates responsibilities:
- `SymNoteInterpreter` handles core logic, variable assignments, type checking, and math.
- `FlowExecutor` handles built-in function calls (`set_bpm`, `load_synth`), track calls, and routines.
- `GridExecutor` parses the time-domain syntax inside `grid{}` blocks and converts them to `MusicalEvent` objects.

## Memory Management & Scoping

SymNote uses **block-level lexical scoping**. This is implemented using an `Environment` class, which acts as a stack of symbol tables (hash maps).

### Environment Frames

Every time the interpreter enters a new block (e.g., an `if` statement, a `while` loop, or a `routine`), it creates a new `Environment` frame that points to its parent frame.

```java
Environment previousEnv = this.env;
this.env = new Environment(previousEnv); // Create new scope

try {
    visit(ctx.block()); // Execute block
} finally {
    this.env = previousEnv; // Pop scope
}
```

When a variable is referenced, the interpreter checks the current frame. If it's not found, it traverses up the parent chain. If it reaches the global frame without finding the variable, an `Undefined variable` error is thrown.

When a block finishes execution (the `finally` block runs), the current frame is discarded, destroying all variables defined within it. This natively supports **variable shadowing**.

### Pass-by-Value and Routine Isolation

Routines in SymNote are strictly isolated. When `FlowExecutor` invokes a routine, it does **not** pass the current local scope as the parent. Instead, it creates a new `Environment` whose parent is explicitly the **Global Scope**.

```java
Environment previousEnv = interpreter.env;
interpreter.env = new Environment(previousEnv.getGlobal()); // Skip local caller scope
```

This guarantees that a routine cannot accidentally (or intentionally) read or mutate the caller's local variables, enforcing clean data flow via parameters and return values.

## Runtime Type Checking

Because SymNote is statically typed, variables lock in their type upon declaration.

The interpreter enforces this via `checkType()` and `checkTypeStrict()` methods.
- `checkType("float", value)` allows an `Integer` to be silently promoted to a `Float` (e.g., `float x = 5;`).
- `checkTypeStrict()` prevents implicit promotion, used during routine return types where exact matching is required.

If a mismatch is detected, the interpreter throws an immediate `RuntimeException` with the exact line number retrieved from the ANTLR context (`ctx.getStart().getLine()`), ensuring clear diagnostics.
