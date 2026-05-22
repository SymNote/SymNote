---
title: Examples
description: Practical SymNote programs demonstrating language features.
sidebar:
  order: 4
---

These examples demonstrate the core features of SymNote.

---

## 1. Hello World — Simple Arpeggio

Loads a synth, defines a track that loops a grid, and calls it.

```ts
set_bpm(120);

synth Piano = load_synth("piano");

track Arpeggio(int bars) {
    use_synth(Piano);
    loop (int i from 1 to bars) {
        grid(1/8) {
            C4 E4 G4 C5 G4 E4 C4 -
        }
    }
}

Arpeggio(2);
```

---

## 2. Multi-Track Song with `parallel`

This example demonstrates a song with three tracks playing simultaneously, with programmatic volume control using boolean logic.

```ts
set_bpm(90);

synth Piano = load_synth("piano");
synth Strings = load_synth("strings");
synth Choir = load_synth("choir");

// Decide mode based on computation
float a = (10.0 - 5.0) * (8.0 + 2.0) * 2.0;
float b = (9.0 - 1.0) / (2.0 + 2.0);
bool dramatic = (a > b) and not (3.0 == 4.0);

float volume = 0.55;
if (dramatic) { volume = 1.0; }

track PianoArp(float v) {
    use_synth(Piano);
    loop (int i from 1 to 2) {
        grid(1/16) {
            D4.vol(v) F4.vol(v) A4.vol(v) D5.vol(v)
            F5.vol(v) D5.vol(v) A4.vol(v) F4.vol(v)
        }
    }
}

track ChordLayer(float v) {
    use_synth(Strings);
    grid(1/1) {
        [D2, D3].vol(v)
        [Bb1, Bb2].vol(v)
        [G1, G2].vol(v)
        [A1, A2].vol(v)
    }
}

track VoiceLayer(float v) {
    use_synth(Choir);
    grid(1/1) {
        [D4, F4, A4].vol(v)
        [Bb3, D4, F4].vol(v)
        [G3, Bb3, D4].vol(v)
        [A3, C#4, E4].vol(v)
    }
}

parallel {
    PianoArp(volume);
    ChordLayer(volume);
    VoiceLayer(volume);
}
```

---

## 3. Grid Resolutions Compared

The same melody at different time resolutions — a common pattern for creating speed variations in different sections:

```ts
set_bpm(120);
synth Synth = load_synth("square");

track FastMelody() {
    use_synth(Synth);
    grid(1/16) {
        C5 D5 E5 F5 G5 F5 E5 D5 C5 - - - - - - -
    }
}

track SlowMelody() {
    use_synth(Synth);
    grid(1/4) {
        C5 D5 E5 F5
    }
}

FastMelody();
SlowMelody();
```
