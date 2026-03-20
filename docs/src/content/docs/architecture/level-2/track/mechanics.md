---
title: Track Mechanics & Logic
description: A complete guide to Level 2, covering control flow, variable scoping, and track definitions.
---

The `track` (Level 2) is the bridge between your song's global arrangement and the raw musical timeline. It acts as a closed, isolated environment where you write the **programming logic** that dictates *when* and *how* the music plays.

A track itself does not make sound. It calculates math, checks conditions, and loops through variables to command the `grid` (Level 3) on what to play.

---

## 1. The Anatomy of a Track

A track is defined using the `track` keyword, followed by a name and a set of input parameters (arguments) enclosed in parentheses.

```ts
track DrumMachine(int bars) {
    // Level 2 Logic goes here
}
```

### Arguments are just variables
It is important to understand that the engine does not inherently know what `bars` means. It is simply an integer variable passed down from Level 1. You use this variable inside your track to control your loops. You could just as easily name it `iterations` or `steps`.

---

## 2. Instrument & State Management

Before a track can generate notes, it needs to know what instrument it is playing and what its base settings are.

* **`use_synth(variable_name);`**: Assigns a previously loaded `synth` to this specific track. All native notes (e.g., `C4`) triggered in child grids will use this synthesizer.
* **`set_vol(float);`**: Sets the baseline volume for the entire track. (Note: You can still override this per-note inside the grid using `.vol()`).

```ts
track SubBass(int bars) {
    use_synth(MyBass); // MyBass was loaded in Level 1
    set_vol(0.85);
    
    // ... logic and grids ...
}
```

:::note[Samples do not need use_synth]
If your track relies entirely on `sample` variables (like `Kick` or `Snare`), you do not need to call `use_synth()`. Samples are triggered directly by their names in the grid.
:::

---

## 3. Control Flow (Directing the Music)

Level 2 supports standard structured programming tools. This allows you to write algorithmic music that changes over time.

### The `loop` Statement (Step Iteration)
The `loop` is the most common tool in Level 2. It is highly readable and perfect for iterating over musical bars.

```ts
loop (int current_bar from 1 to 4) {
    // This will execute 4 times.
    // current_bar will be 1, then 2, then 3, then 4.
}
```

### The `while` Statement (Condition Iteration)
If you need a loop that depends on a specific condition rather than a strict step count, use `while`.

```ts
int intensity = 10;
while (intensity > 0) {
    // ... play music ...
    intensity = intensity - 2;
}
```

### The `if / else` Statement (Branching)
Conditionals are used to create structural variations, such as playing a drum fill at the end of a phrase.

```ts
track DrumPattern(int total_bars) {
    loop (int i from 1 to total_bars) {
        
        // Check if we are on the very last bar
        if (i == total_bars) {
            // Play a dense fill
            grid (1/16) { Snare Snare Snare Snare }
        } else {
            // Play the standard beat
            grid (1/4) { Kick - Snare - }
        }
    }
}
```

---

## 4. Lexical Scoping (The Isolation Rule)

A track is a secure environment. This means:
1. **Variables do not leak out:** If you declare `int current_beat = 1;` inside a track, Level 1 and other tracks cannot see it.
2. **Block-level destruction:** Variables declared inside an `if` or `loop` block are destroyed as soon as the block ends `}`. 
3. **Shadowing:** You can declare a local variable with the same name as a Level 1 global variable. The local variable will temporarily "shadow" (override) the global one until the block ends.

---

## 5. Strict Level 2 Rules

To maintain the architectural integrity of SymNote, the semantic analyzer enforces the following restrictions inside a track:

* **No concurrency:** You **cannot** use the `parallel` keyword. Concurrency is strictly a Level 1 feature used to arrange the final tracks.
* **No nested tracks:** You **cannot** define a `track` inside another `track`.
* **No routine declarations:** Mathematical functions (`routine`) must be declared globally at Level 1, though you are free to *call* them inside Level 2.