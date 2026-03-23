---
title: The 3-Level Architecture
description: Understanding the strict top-down hierarchy of the SymNote language.
---

To make SymNote deterministic, 100% predictable, and musically accurate, the engine relies on a strict top-down hierarchy. Higher-level objects can contain lower-level objects, but never the reverse. 

Understanding these three levels is the key to mastering SymNote.

---

## Level 1: Global Context (The Timeline)
This is the highest level of your `.symnote` file. Time at this level flows in "blocks" - every track you call blocks the main clock for its duration, and then passes it forward.

This level is exclusively for **configuration, resource loading, and arrangement**.

### What you can do here:
* Set the global tempo using `set_bpm()`.
* Declare global variables (`int`, `bool`, `float`).
* Load instruments into memory (`load_sample`, `load_synth`).
* Declare mathematical or logical `routine` functions.
* Define `track` templates.
* Arrange the song using sequential calls, `parallel` blocks, and `loop` blocks.

```ts
// Time Configuration
set_bpm(130);

// Global Variables
bool play_drums = true;

// Resource Loading
sample Kick = load_sample("kick.wav");
synth Lead = load_synth("sawtooth");

// Track Definitions (Templates)
track DrumMachine(int bars) { /* Level 2 Logic */ }
track Bassline(int bars) { /* Level 2 Logic */ }

// Timeline Arrangement (Execution)
parallel {
    DrumMachine(4);
    Bassline(4);
}
```

---

## Level 2: Track Context (The Logic)
Defined within `track Name(arguments) { ... }`, this is a closed environment for a specific musical part. Variables created here **do not leak out** into the global scope.

This level bridges standard programming logic with musical generation.

### What you can do here:
* Assign instruments using `use_synth()`.
* Set track-specific volume using `set_vol()`.
* Declare local variables (these will destroy themselves when the track finishes).
* Use control flow logic (`if`/`else`, `while`, `loop`).
* Call the Level 3 `grid` to actually play notes.

:::danger[Rule]
You **cannot** use the `parallel` keyword or define new tracks within Level 2.
:::

```ts
track LeadSynth(int bars) {
    use_synth(Lead);
    set_vol(0.75);

    int current_bar = 0;

    // Programming logic controlling the music flow
    while(current_bar < bars) {
        if (play_drums) {
            loop(int i from 1 to 2) {
                // Calling the lowest level (Level 3)
                grid (1/16) { /* Level 3 Time Grid */ }
            }
        }
        current_bar = current_bar + 1;
    }
}
```

---

## Level 3: Grid Context (The Piano Roll)
Defined within `grid (resolution) { ... }`, this is where programming logic stops, and raw time begins. Time flows from left to right with every written symbol. 

Whitespace (spaces, tabs, and line breaks/Enters) is **required to separate** individual symbols, notes, or variables. However, whitespace **does not affect the flow of time**. A line break exists only to organize your code visually—the engine's parser reads the entire grid as one continuous sequence of time.

The resolution (e.g., `1/16`) dictates the duration of a **single valid symbol**.

### What you can do here:
* Play standard notes (`C4`, `D4`).
* Play loaded samples or synths by calling their variable names (e.g., `Kick`, `Snare`).
* Manipulate time with rests (`-` moves time forward in silence).
* Sustain notes (`~` holds the previous note).
* Play chords or simultaneous hits (`[C4, E4, G4]`).
* Override volume per-note using `.vol()`.

:::danger[Strict Rule]
You **cannot** use `if`, `while`, `loop`, or declare variables inside a `grid`. This is a pure time-execution environment.
:::

```ts
// Assuming we are inside a track
grid (1/16) {
    // Standard notes separated by spaces
    C4 D4 E4 ~
    
    // Line breaks do not add extra time. 
    // The Kick here plays immediately after the '~' above.
    Kick - Snare -
    
    // Polyphony (Multiple hits in the exact same millisecond)
    [C4, E4, G4] - - -

    // Per-hit / chord volume override
    Kick.vol(1.0) Kick.vol(0.5) C4.vol(my_variable) [C4, E4].vol(0.7)
}