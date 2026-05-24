---
title: Diagnostics & Error Handling
description: Understanding SymNote's error messages and diagnostic features
sidebar:
  order: 5
---

# Advanced Diagnostics in SymNote

SymNote provides a robust set of error diagnostics designed to help you  identify and resolve issues in your code.

## 1. "Did You Mean?" Suggestions

When you misspell the name of a variable, routine, or track, SymNote will scan your current environment (and outer scopes) to find the closest matching identifier and suggest it to you.

```c
int volume = 80;
print(volum); // Typo!
```
**Error Output:**
```
Error: Undefined variable 'volum' at line 2 (Did you mean 'volume'?)
```

## 2. Call Stack Traces

If a runtime error occurs deeply nested inside routines or tracks, SymNote attaches a call stack trace to the error message.

```c
routine divide(int a, int b) returns int {
    return a / b;
}

routine process() returns void {
    int res = divide(10, 0);
}

process();
```
**Error Output:**
```
Error: Division by zero at line 2
Call stack:
  at routine 'divide'
  at routine 'process'
```

## 3. Rich Type Mismatches

SymNote will explain when you provide the wrong type for a variable, return statement, or operator.

```c
int speed = 3.14;
```
**Error Output:**
```
Error: Type mismatch: 'speed' is declared as 'int' but got float (value: 3.14) at line 1
```

## 4. Built-in Function Validation

SymNote functions have strict internal validation to prevent unexpected behavior. 

- **`set_bpm()`**: Requires a positive integer or float. Passing zero, a negative number, or a string will explain exactly why the value was rejected.
- **`load_synth()`**: Demands a string representing the instrument name.
- **`use_synth()`**: Enforces that you pass `synth` variable.

```c
track PianoTrack() {
    use_synth("piano"); // Error: Should be a loaded synth variable
}
parallel { PianoTrack(); }
```
**Error Output:**
```
Error: use_synth() requires a synth variable, but got string at line 2. Did you forget to assign the result of load_synth()?
```
**Correct Usage:**
```c
synth myPiano = load_synth("piano");
track PianoTrack() {
    use_synth(myPiano);
}
parallel { PianoTrack(); }
```

## 5. Grid Note Resolution Validation

The `grid()` block is highly specialized for musical notes. If you try to place a variable inside the grid that does not resolve to a valid note, SymNote will clearly identify the variable and its invalid contents.

```c
string myNote = "C4"; // Should be type 'note', not 'string'
synth myPiano = load_synth("piano");
track MyTrack() {
    use_synth(myPiano);
    grid(1/4) {
        myNote - - -
    }
}
parallel { MyTrack(); }
```
**Error Output:**
```
Error: Variable 'myNote' is of type 'string' but grid requires a 'note' type at line 6
```

## 6. Infinite Recursion Protection

If you create an infinite recursive loop, SymNote's internal call stack catches the depth limit, throwing SymNote StackOverflow error pointing out where the recursion got stuck.
