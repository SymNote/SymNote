---
title: Language Rules & Errors
description: Semantic constraints, context limitations, and the error reporting system.
---

While syntax defines the grammar of SymNote, **Rules** define its logic. The semantic analyzer enforces these constraints during the interpretation phase (via the AST Visitor) before any audio is generated.

Breaking these rules will result in a compilation error, preventing the engine from running invalid or unpredictable logic.

---

## 1. Contextual Constraints
SymNote's 3-Level Architecture heavily restricts what keywords can be used where.

### Level 2 Restrictions (Inside a `track`)
* **No concurrency:** You cannot use the `parallel` block inside a track. Concurrency is strictly a Level 1 (Timeline) operation.
* **No nested tracks:** You cannot define a new `track` inside another `track`.
* **No routine declarations:** Functions (`routine`) must be declared globally.

### Level 3 Restrictions (Inside a `grid`)
The grid is a pure time-execution environment. To guarantee deterministic audio timing, all complex logic is stripped away.
* **No control flow:** `if`, `else`, `while`, and `loop` are strictly forbidden.
* **No variable declarations:** You cannot declare or assign values to variables (`int x = 5;`) inside a grid.
* **No mathematical expressions:** You cannot calculate values inside a grid (e.g., `C4.vol(0.5 + 0.2)` is invalid). Calculate the value in Level 2 and pass the variable instead: `C4.vol(my_calc)`.

---

## 2. Type Safety & Scoping Rules

SymNote relies on strict static typing to ensure the Java audio engine never crashes during playback.

### No Implicit Casting
The engine will not automatically convert types for you. 
* You cannot pass a `float` into a function that expects an `int`.
* You cannot use an `int` as a condition for an `if` statement (it must resolve to a `bool`).

### Routine Return Matching
If a `routine` specifies a `returns` type, every possible execution path within that routine **must** return a value of that exact type.

```ts
// INVALID: What happens if velocity is < 50? The engine wouldn't know what to return.
routine get_amp(int velocity) returns float {
    if (velocity > 50) {
        return 0.8;
    }
}
```

### Lexical Scope Violation (Undefined Variables)
Attempting to read or modify a variable that has not been declared in the current or parent scope will result in an `Undefined Variable` error. Remember that variables declared inside blocks `{ }` are destroyed when the block ends.

---

## 3. The Error Reporting System

When SymNote encounters a semantic or syntactic rule violation, it immediately halts compilation and generates a precise diagnostic report.

The engine locates the exact file, line, and column of the offending token and wraps it in visual markers (`> <`) to help developers quickly identify the issue.

### Anatomy of an Error
A standard SymNote console error looks like this:

```text
File: 'song.symnote'
Location: Line 15, Column 16
Description: Type mismatch. Cannot assign an integer to a boolean variable.
14 | set_bpm(120);
15 | bool my_var = >15<;
16 | sample Kick = load_sample("kick.wav");
```

* **File:** The specific script being executed.
* **Location:** The exact Line and Column number determined by the ANTLR4 Token stream.
* **Description:** A human-readable explanation of which semantic or syntactic rule was broken.
* **Snippet:** A 3-line preview showing the exact context, with the problematic token clearly highlighted.