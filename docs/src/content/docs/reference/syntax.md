---
title: Syntax Reference
description: The formal grammar, reserved keywords, and operators of SymNote.
---

This document serves as the "dictionary" of SymNote. It outlines all reserved keywords, operators, and grammatical rules that the ANTLR4 lexer and parser expect.

---

## 1. Reserved Keywords
You cannot use these words as variable names. They are structurally bound to the engine's core logic.

### Data Types & Values
* `int`, `float`, `bool`, `string` - Standard primitives.
* `note`, `synth`, `sample` - Audio domain objects.
* `true`, `false` - Boolean literal values.

> **Warning: Reserved Note Literals**
> Variables cannot be named like musical notes (e.g., `A4`, `C#3`, or `Bb2`). These patterns are strictly reserved as `NOTE` literals by the lexer and cannot be used as identifiers (`ID`).

### Control Flow & Structure
* `track` - Defines a Level 2 musical blueprint.
* `routine` - Defines a mathematical/logical function.
* `returns` - Specifies the return type of a `routine`.
* `parallel` - Executes multiple tracks simultaneously (Level 1 only).
* `grid` - Enters the Level 3 time execution environment.
* `if`, `else` - Standard conditional branching.
* `loop`, `from`, `to` - Step-based iteration (e.g., `loop (int i from 1 to 4)`).
* `while` - Condition-based iteration.

### Built-in Audio Functions
* `set_bpm` - Configures the global tempo.
* `set_vol` - Sets the master volume for the current scope.
* `load_sample`, `load_synth` - Allocates audio resources.
* `use_synth` - Assigns an instrument to the current track.

---

## 2. Operators & Precedence
SymNote enforces strict mathematical order of operations during AST evaluation. 

### Arithmetic Operators
Evaluated mathematically.
1. `*`, `/`, `%` (Multiplication, Division, Modulo) - *Highest priority*
2. `+`, `-` (Addition, Subtraction)

### Relational Operators
Used to compare values. These always return a `bool`.
* `<`, `>`, `<=`, `>=` (Less than, Greater than, etc.)
* `==`, `!=` (Equal to, Not equal to)

### Logical Operators
Unlike C or Java which use symbols (`&&`, `||`, `!`), SymNote uses highly readable text keywords for logical operations.
1. `not` - Logical negation (Highest logical priority)
2. `and` - Logical conjunction
3. `or` - Logical disjunction

```ts
// Example of logical precedence
bool play_fx = (velocity == 100) and (is_chorus_on or not is_loud);
```

---

## 3. Grid-Specific Tokens
These tokens hold special meaning **only** inside a `grid {}` block.

* `[A-G][#b]?[0-9]` (e.g., `C4`, `F#2`, `Bb3`) - Native note literals. Recognized automatically by the lexer.
* `-` (Rest) - Advances the timeline by one resolution unit in silence.
* `~` (Sustain) - Holds the previous note/sample for an additional resolution unit.
* `[ ... ]` (Chord brackets) - Groups multiple notes or samples to be triggered in the exact same millisecond.
* `,` (Comma) - Separates elements within a chord bracket.
* `.vol(...)` - A special method call applied directly to a hit to override its volume.

---

## 4. Punctuation
* `{ }` (Curly Braces) - Define a block scope (Environment Frame).
* `( )` (Parentheses) - Group mathematical equations, define arguments, or define grid resolution.
* `;` (Semicolon) - **Required** to terminate any standard declaration or statement outside of a `grid`.
* `//` (Comments) - Ignores everything after the slashes until the end of the line.

```ts
// This is a comment. The compiler ignores this.
int x = 10; // Inline comments are also valid.
```