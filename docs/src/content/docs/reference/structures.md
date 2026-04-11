---
title: Data Structures & Variables
description: The static type system, memory management, and lexical scoping rules in SymNote.
---

SymNote utilizes a **statically typed** system. Every variable must be explicitly declared with its type before use. There is no implicit type coercion between primitives (e.g., you cannot pass a `float` where an `int` is expected without explicit engine support).

This strictness ensures that the underlying Java audio engine receives exact, predictable data types to prevent runtime audio dropouts or buffer errors.

---

## 1. Primitive Types
These map directly to standard computing primitives in the interpreter's host language.

* **`int`**: A 32-bit signed integer. Used primarily for counting (e.g., loops, steps, iterations) and strict MIDI values.
  * *Example:* `int bars = 4;`
* **`float`**: A 32-bit floating-point number. Used for continuous audio parameters like volume, panning, detuning, and frequency math.
  * *Example:* `float master_vol = 0.85;`
* **`bool`**: A boolean logic type. Accepts only `true` or `false`. Crucial for control flow (`if`/`while`).
  * *Example:* `bool play_chorus = true;`

---

## 2. Domain-Specific Types
These types are native to SymNote and represent actual audio objects or concepts in the engine's memory space.

* **`note`**: Represents a musical pitch. You generally do not declare these manually; they are built-in native constants mapped directly to mathematical frequencies (e.g., `A4` resolves natively to `440.0` Hz, `C2` to `65.4` Hz).
* **`sample`**: A reference pointer to an audio buffer loaded in memory.
  * *Declaration:* `sample Kick = load_sample("kick.wav");`
  * *Engine Note:* The interpreter should load the file from disk once, store it in a global buffer pool, and assign the variable as a reference pointer to avoid redundant memory allocation.
* **`synth`**: A symbolic instrument name used by the MIDI renderer.
  * *Declaration:* `synth Lead = load_synth("sawtooth");`
  * *Engine Note:* The value returned by `load_synth(...)` is mapped to a General MIDI program.
  * *Canonical names currently supported:* `piano`, `organ`, `bass`, `guitar`, `strings`, `square`, `sawtooth`, `pad`, `choir`, `trumpet`, `sax`, `flute`, `bell`, `pluck`.

---

## 3. Variable Declaration and Assignment
Variables are declared using the format `type name = value;`. 

```ts
// Valid declarations
int transpose = -12;
float delay_feedback = 0.5;

// Re-assignment (Type is omitted because it already exists in the symbol table)
transpose = 0; 
```

---

## 4. Lexical Scoping and Shadowing
SymNote enforces **Block-Level Lexical Scoping**. 

Whenever the engine enters a new set of curly braces `{ ... }` (whether for a `track`, `routine`, `if` statement, or `loop`), the interpreter pushes a new **Environment Frame** onto the Symbol Table stack.

### Rule 1: Isolation and Destruction
Variables declared inside a block exist *only* within that block. When the execution exits the closing brace `}`, the environment frame is popped, and the variables are destroyed.

### Rule 2: Variable Shadowing
A variable declared in a nested local scope can share the same name as a global variable. The local variable will "shadow" (temporarily override) the global one. Once the local scope ends, the original global value is restored.

```ts
// Global Scope (Level 1)
int offset = 12;

if (true) {
    // Local Scope
    float local_amp = 0.8; 
    
    // Valid Shadowing: This temporarily overrides the global 'offset'
    int offset = 24; 
    
    // Here, offset == 24
}

// Scope destroyed.
// Here, offset == 12 again.
// Calling 'local_amp' here will throw an "Undefined Variable" compile error.
```

---

## 5. Pass-By-Value
When passing variables into a `routine`, SymNote passes them strictly **by value**. 

This means the function receives a secure copy of the variable. Modifying an argument inside a routine will **never** alter the original variable in the outer scope. This guarantees thread safety and prevents unintended side effects during complex, recursive mathematical operations.

```ts
routine add_octave(int pitch) returns int {
    // We modify the local copy of 'pitch'
    pitch = pitch + 12;
    return pitch;
}

int base_note = 60;
int new_note = add_octave(base_note);

// base_note is STILL 60.
// new_note is 72.
```