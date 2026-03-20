---
title: Routines & Recursion
description: How to write mathematical functions, ensure thread safety, and use recursion for musical effects.
sidebar:
  order: 2
---

While a `track` organizes the timeline and a `grid` plays the notes, a `routine` handles the math.

A `routine` in SymNote is a pure mathematical and logical function. It does not generate audio or interact with the timeline directly. Instead, it calculates parameters—like volume, panning, or filter cutoff—that your tracks can use to make the music dynamic.

---

## 1. Anatomy of a Routine

Because routines are meant to be used by any track in your song, they **must be declared at Level 1** (the global context). 

A routine must strictly define its input arguments and its return type using the `returns` keyword.

```ts
// A simple routine that calculates a dynamic volume
routine get_accent_vol(bool is_downbeat) returns float {
    if (is_downbeat) {
        return 1.0;
    } else {
        return 0.5;
    }
}
```

### The "Return" Guarantee
The SymNote compiler strictly enforces return paths. If your routine specifies `returns float`, **every possible execution path** within that routine must return a `float`. If the engine detects a scenario where the routine might end without returning a value, it will throw a compile-time error.

---

## 2. Pass-By-Value (Thread Safety)

When you pass a variable into a routine, SymNote uses **pass-by-value**. This means the routine receives a secure *copy* of the variable, not the original one.

This is a critical safety feature. Because SymNote can run multiple tracks concurrently using the `parallel` block, allowing a routine to modify global variables could cause catastrophic race conditions in the audio engine. 

```ts
int base_pitch = 60;

routine add_octave(int pitch) returns int {
    pitch = pitch + 12; // Modifying the local copy
    return pitch;
}

int new_pitch = add_octave(base_pitch);

// new_pitch is now 72.
// base_pitch safely remains 60.
```

---

## 3. Recursion in SymNote

Recursion occurs when a routine calls itself. In standard programming, recursion is often used for sorting or traversing data. In SymNote, recursion is the most elegant way to calculate **exponential musical curves**, such as a natural fade-out (decay) or an accelerating drum roll.

### The Recursive Fade-Out (Decay)

A natural sound (like a plucked guitar string) doesn't fade out linearly; it fades out exponentially, losing a fraction of its remaining energy over time. We can perfectly model this using a recursive routine.

To write a safe recursive routine, you must always include a **Base Case** (the condition that stops the recursion) to prevent an infinite loop.

```ts
// This routine reduces the volume by 20% for every step
routine calc_decay(int step) returns float {
    
    // 1. The Base Case: If it's the very first step, return full volume
    if (step <= 1) { 
        return 1.0; 
    }
    
    // 2. The Recursive Case: Take the volume of the PREVIOUS step, and multiply it by 0.8
    return calc_decay(step - 1) * 0.8;
}
```

### Applying the Recursion to Music

Now, we can use this recursive routine inside a Level 2 `track` to apply a beautiful, natural fade-out to a sequence of notes in our `grid`.

```ts
track PluckArp(int total_notes) {
    use_synth(Pluck); // Assuming 'Pluck' is loaded in Level 1
    
    loop (int current_note from 1 to total_notes) {
        
        // Calculate the exact volume for this specific note
        float dyn_vol = calc_decay(current_note);
        
        // Execute one step on the timeline with the calculated volume
        grid (1/16) {
            C4.vol(dyn_vol)
        }
    }
}

// In Level 1, we call the track to play 16 notes.
// Note 1 will be vol(1.0)
// Note 2 will be vol(0.8)
// Note 3 will be vol(0.64), and so on...
PluckArp(16);
```

### Why use recursion over a standard loop?
While you *could* calculate a linear fade-out using standard division in a Level 2 loop, a recursive routine keeps your track logic clean and allows you to reuse the exact same decay curve across multiple different tracks and instruments. It separates the "math" from the "music."