---
title: Grid Mechanics & Patterns
description: A complete guide to Level 3, covering symbols, strict sequencing rules, and advanced dynamic patterns.
---

The `grid` (Level 3) is the heart of SymNote's music generation. This is where programming logic ends, and the pure execution of musical time begins. 

This document covers every symbol you can use inside a grid, the strict rules governing their placement, and the design patterns needed to create dynamic music.

---

## 1. The Symbols (Grid Dictionary)

Inside a `grid` block, standard programming keywords are disabled. Instead, the engine only understands a specific set of musical and temporal symbols.

### 🎵 Pitched Notes (Driven by Level 2 Synth)
Notes do not contain audio themselves; they rely on the `synth` assigned to the `track` via `use_synth()`.
* **Syntax:** Standard scientific pitch notation.
* **Examples:** `C4`, `F#2`, `Bb3`

### 🥁 Samples (Direct Triggers)
Unlike notes, variables representing loaded `sample` objects carry their own audio buffer and are triggered directly by their variable name.
* **Examples:** `Kick`, `Snare`, `ClosedHat`

### ⏱️ Time Manipulators
Time in the grid flows from left to right. These symbols control that flow.
* **`-` (Rest):** Advances the timeline by one resolution step in complete silence.
* **`~` (Sustain):** Holds the previously triggered note or sample for an additional resolution step.

### 🎹 Polyphony (Chords)
* **`[ ... ]` (Brackets):** Groups multiple sounds together so they trigger at the exact same logical microsecond.
* **`,` (Comma):** Separates the individual elements inside the polyphony brackets.
* **Examples:** `[C4, E4, G4]`, `[Kick, Snare]`

---

## 2. Strict Sequencing Rules

To ensure the Java audio engine receives valid instructions, the ANTLR4 parser enforces strict rules on how symbols can be placed in sequence.

### The Sustain (`~`) Rules
The sustain symbol extends a sound, which means a sound must already be playing.
* **Rule 1:** A grid **cannot** begin with `~` unless a previous grid in the same loop left a note ringing. If it's the very first symbol of a track, it is a compilation error.
* **Rule 2:** You **cannot** place a `~` immediately after a rest (`-`). You cannot sustain silence.
* **Valid:** `C4 ~ ~ -`
* **Invalid:** `~ C4 -` or `C4 - ~`

### The Polyphony (`[ ]`) Rules
* **Rule 1:** Brackets can **only** contain Notes or Samples. 
* **Rule 2:** You **cannot** place Rests (`-`) or Sustains (`~`) inside a chord bracket.
* **Valid:** `[C4, E4]` followed by a single `~` (which sustains the entire chord).
* **Invalid:** `[C4, ~]` or `[Kick, -]`

### The Volume Modifier (`.vol()`) Rules
* **Rule 1:** The `.vol()` modifier can only be attached to a Note, a Sample, or a closed Chord bracket.
* **Rule 2:** It **cannot** be attached to a Rest or a Sustain.
* **Valid:** `Kick.vol(0.8) - C4.vol(my_var)`
* **Invalid:** `-.vol(0.5)` or `~.vol(1.0)`

---

## 3. Whitespace and Formatting

A very common question is: *Do spaces or line breaks (Enters) affect the timing?*

**No. Whitespace does not advance time.** Spaces, tabs, and line breaks are completely ignored by the engine's timeline. They are strictly required to separate individual tokens (so the parser knows `C4` and `D4` are two different notes), but they do not create rests or pauses.

```ts
// These two grids produce the EXACT same musical timing:

// Example 1: Horizontal
grid (1/16) { C4 D4 E4 ~ }

// Example 2: Vertical
grid (1/16) { 
    C4 
    D4 
    E4 
    ~ 
}
```

---

## 4. Resolution: Step Size vs. Capacity

The resolution parameter in the grid declaration e.g., `grid (1/16)` or `grid (1/8)` is crucial to understand. 

**It does not mean "this block must contain exactly 16 notes."** Instead, it means: **"Every single symbol inside this block has a duration of exactly 1/16th of a bar."**

You can put 1 symbol inside a `grid(1/16)` or 100 symbols. The engine will simply advance the timeline by one 1/16th step for each symbol it encounters, one after the other.

---

## 5. Design Patterns: Overcoming Grid Limitations

Because the `grid` prohibits math and function calls to maintain perfect audio timing, you cannot calculate dynamic parameters directly on a note (e.g., `C4.vol(0.5 + 0.2)` is invalid). 

To create dynamic effects, use the **SymNote Micro-Grid Pattern**.

### Pattern A: Manual Stepping (For short fills)
If you are doing a quick drum fill or a specific humanized groove, hardcode the floating-point values directly into a single grid.

```ts
track SnareFill(int bars) {
    grid (1/16) {
        Snare.vol(0.2) Snare.vol(0.4) Snare.vol(0.6) Snare.vol(1.0)
    }
}
```

### Pattern B: The Micro-Grid Loop (For algorithmic fades)
If you want a smooth mathematical fade-out, calculate the volume in a **Level 2 loop** and pass it to a 1-beat `grid`. 

Because a grid resolution dictates the *step size*, placing a 1-note grid inside a loop causes the engine to seamlessly chain those steps together on the timeline without needing rests (`-`).

```ts
track FadeOutLead(int total_steps) {
    use_synth(Lead);
    
    float current_vol = 1.0;
    float fade_amount = current_vol / total_steps;
    
    loop (int i from 1 to total_steps) {
        
        // 1. Calculate the math safely in Level 2 (Takes zero musical time)
        current_vol = current_vol - fade_amount;
        
        // 2. Execute exactly one 1/16th step on the timeline
        grid (1/16) {
            C4.vol(current_vol) 
        }
    }
}
```