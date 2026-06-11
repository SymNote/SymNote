---
title: Language Guide
description: The complete user guide to the SymNote language — types, control flow, scoping, and musical structures.
sidebar:
  order: 3
---

SymNote is a **statically typed, imperative language** designed for rendering MIDI music. Every variable must be declared with an explicit type.

---

## Variables & Data Types

### Primitive Types

| Type     | Description                          | Example                     |
|----------|--------------------------------------|-----------------------------|
| `int`    | Whole number (32-bit signed)         | `int bars = 4;`             |
| `float`  | Decimal number                       | `float vol = 0.75;`         |
| `bool`   | Boolean — `true` or `false`          | `bool active = true;`       |
| `string` | Text value, in double quotes         | `string name = "intro";`    |

### Audio Types

| Type     | Description                                                    | Example                              |
|----------|----------------------------------------------------------------|--------------------------------------|
| `synth`  | A MIDI instrument loaded by name                               | `synth Piano = load_synth("piano");` |
| `note`   | A musical pitch. Used as literals (`C4`) or variables.           | `note n = C4;`                      |

### Declaring Variables

```ts
int transpose = -12;
float delay_feedback = 0.5;
bool play_chorus = true;
string label = "intro";
```

A variable must be declared before it is used. Declaration and assignment can be split:

```ts
int x;      // declared, not yet initialized
x = 10;     // assigned later
```

Attempting to read an uninitialized variable is a runtime error.

### Reassignment

Omit the type keyword when reassigning:

```ts
int x = 5;
x = 10;      // correct — just the new value
```

### Type Rules

- `float` accepts both `int` and `float` literals. `int` does **not** accept `float`.
- Explicit casting is supported: `(int)2.5` yields `2`.
- `bool` cannot be used as a number, and an `int` cannot be used as a `bool`.

```ts
float x = 3;       // OK — int literal promoted to float
int y = 3.5;       // ERROR — float cannot be silently assigned to int
int z = (int)3.5;  // OK — explicit cast, result is 3
```

---

## Operators

### Arithmetic

```ts
int a = 10 + 3;   // 13
int b = 10 - 3;   // 7
int c = 10 * 3;   // 30
int d = 10 / 3;   // 3  (integer division)
int e = 10 % 3;   // 1  (modulo)
float f = 10 / 3.0; // 3.3333...
```

Operator precedence (high to low):
1. Unary `-` and `+`
2. `*`, `/`, `%`
3. `+`, `-`
4. Comparison: `<`, `>`, `<=`, `>=`, `==`, `!=`
5. `not`
6. `and`
7. `or`

### Logical Operators

SymNote uses plain English keywords instead of symbols:

```ts
bool a = true;
bool b = false;

bool c = a and b;        // false
bool d = a or b;         // true
bool e = not a;          // false
bool f = (not b) and a;  // true
```

`and` and `or` are **short-circuit** evaluated:
- `false and <anything>` → `false`, the right side is never evaluated
- `true or <anything>` → `true`, the right side is never evaluated

### Increment & Decrement

Only works on `int` variables:

```ts
int i = 0;
i++;   // i is now 1
i--;   // i is now 0
++i;   // i is now 1 (pre-increment)
```

---

## Control Flow

### if / else

The condition **must** evaluate to a `bool`. An integer is not a valid condition.

```ts
int x = 10;

if (x > 5) {
    print("big");
} else {
    print("small");
}
```

Single-statement `if` (without braces) is allowed, but the variable declared inside does **not** leak out:

```ts
if (true)
    int hidden = 99;

// print(hidden);  // ERROR — hidden is out of scope here
```

### while Loop

```ts
int i = 0;
while (i < 5) {
    i++;
    print(i);
}
// prints: 1 2 3 4 5
```

### loop (for-range)

The `loop` construct iterates over an inclusive integer range. The loop variable is scoped to the loop body:

```ts
loop (int i from 1 to 4) {
    print(i);
}
// prints: 1 2 3 4
```

You can use the same variable name in sequential loops — they don't conflict:

```ts
loop (int i from 1 to 2) { print(i); }
loop (int i from 1 to 2) { print(i); }
// prints: 1 2 1 2
```

### break & continue

`break` exits the innermost loop. `continue` skips to the next iteration. Both work inside `loop` and `while`:

```ts
loop (int i from 1 to 5) {
    if (i == 3) { break; }
    print(i);
}
// prints: 1 2
```

---

## Scoping

SymNote uses **block-level lexical scoping**. Every `{ }` block creates a new scope frame. Variables declared inside are destroyed when the block ends.

```ts
int x = 10;

if (true) {
    int y = 20;
    int x = 99;   // shadows the outer x
    print(x);     // prints: 99
}

print(x);         // prints: 10  (original restored)
// print(y);      // ERROR — y is out of scope
```

### Shadowing and Parent Scope

A nested scope may declare a variable with the same name as an outer scope. The inner variable temporarily shadows the outer one. The outer value is untouched.

To access or modify the hidden variable from the parent scope, SymNote provides the `parent::` keyword. You can chain this keyword (e.g., `parent::parent::`) to access scopes further up the hierarchy.

```ts
int a = 1;
{
    int a = 5;
    int x = a; // x is 5 (from current scope)
    int y = parent::a; // y is 1 (from the outer scope)
    
    // You can also assign to parent variables:
    parent::a = 10; 
}
print(a); // prints: 10
```

---

## Routines (Functions)

Routines are named, reusable functions. They must be declared at the **global** (Level 1) scope.

```ts
routine add(int a, int b) returns int {
    return a + b;
}

int result = add(3, 7);
print(result);  // prints: 10
```

### Return Types

Every routine must declare its return type after `returns`. Use `void` if nothing is returned:

```ts
routine greet(string name) returns void {
    print(name);
}

greet("SymNote");
```

Routines with a non-void return type **must always return a value**. All paths must return:

```ts
// ERROR: what if condition is false?
routine bad(int x) returns int {
    if (x > 0) {
        return x;
    }
}
```

### Pass-By-Value

All arguments are passed by value — modifying a parameter inside a routine never affects the caller's variable:

```ts
routine double_it(int x) returns int {
    x = x * 2;
    return x;
}

int original = 5;
int doubled = double_it(original);
print(original);  // prints: 5 — unchanged
print(doubled);   // prints: 10
```

### Recursion

Routines can call themselves:

```ts
routine factorial(int n) returns int {
    if (n <= 1) { return 1; }
    return n * factorial(n - 1);
}

print(factorial(5));  // prints: 120
```

---

## The 3-Level Architecture

SymNote programs are structured in three strict levels. Code from a lower level cannot be used at a higher level.

### Level 1 – Global Timeline

The top level of your `.symnote` file. This is where you configure the song, load instruments, define tracks and routines, and arrange the timeline.

```ts
set_bpm(130);                              // global tempo

synth Lead = load_synth("sawtooth");       // load instrument

routine helper(int x) returns int { ... } // declare routine

track Intro(int bars) { ... }             // define track
track DrumLoop(int bars) { ... }

Intro(4);                                  // call track sequentially

parallel {                                 // play tracks at the same time
    DrumLoop(4);
    Intro(4);
}
```

**What is allowed:** `set_bpm`, `load_synth`, global variables, `routine` declarations, `track` declarations, `loop`, `while`, `if`, `parallel`, sequential track calls.

**What is NOT allowed:** `use_synth`, `grid` — these belong to Level 2 and Level 3.

### Level 2 – Track Body

Defined with `track Name(params) { ... }`. A track is a blueprint for a musical part. It bridges logic and music generation.

```ts
track Melody(int bars) {
    use_synth(Lead);      // assign instrument

    int step = 0;
    while (step < bars) {
        if (step % 2 == 0) {
            grid(1/8) { C5 E5 G5 - C5 E5 G5 - }
        } else {
            grid(1/8) { G4 B4 D5 - G4 B4 D5 - }
        }
        step++;
    }
}
```

**What is allowed:** `use_synth`, local variables, `if`, `while`, `loop`, `grid`, routine calls.

**What is NOT allowed:** `parallel`, nested `track` declarations.

### Level 3 – Grid (Piano Roll)

The `grid` block is a pure time-execution environment. No logic is allowed here — only notes and rhythm.

```ts
grid(1/16) {
    C4 D4 E4 F4
    G4 ~ ~ -
    [C4, E4, G4] - - -
    C4.vol(1.0) E4.vol(0.8) - -
}
```

**Resolution** (`1/1`, `1/2`, `1/4`, `1/8`, `1/16`, `1/32`) sets the duration of each symbol.

| Symbol         | Meaning                                             |
|----------------|-----------------------------------------------------|
| `C4`, `F#3`    | Play a note (absolute pitch)                        |
| `-`            | Rest — advance time by one resolution step          |
| `~`            | Sustain — extend the previous note one more step    |
| `[A, B, C]`    | Chord — play multiple notes simultaneously          |
| `.vol(x)`      | Volume override for a note, variable, or chord      |

**What is NOT allowed inside a grid:** `if`, `else`, `while`, `loop`, variable declarations or assignments, arithmetic expressions.

### parallel

Plays multiple tracks simultaneously. Only track calls are allowed inside:

```ts
parallel {
    DrumLoop(4);
    Melody(4);
    Bassline(4);
}
```

---

## Built-in Functions

| Function                     | Description                                     |
|------------------------------|-------------------------------------------------|
| `set_bpm(int)`               | Set the global tempo (Level 1 only)             |
| `use_synth(synth)`           | Assign an instrument to the current track       |
| `load_synth(string)`         | Load a named MIDI instrument                    |
| `print(value)`               | Print a value to stdout                         |

### Supported synth names for `load_synth`:

`"piano"`, `"organ"`, `"bass"`, `"guitar"`, `"strings"`, `"square"`, `"sawtooth"`, `"pad"`, `"choir"`, `"trumpet"`, `"sax"`, `"flute"`, `"bell"`, `"pluck"`

---

## Comments

```ts
// This is a single-line comment. The interpreter ignores it.
int x = 5; // Inline comments are also valid.
```
