---
title: ANTLR Grammar Design
description: How the SymNote language is defined using ANTLR4.
sidebar:
  order: 2
---

The SymNote parser is generated using ANTLR4. The grammar definition (`SymNote.g4`) is responsible for both defining the language syntax and enforcing the strict 3-Level Architecture.
## Enforcing the Architecture via Rules

Instead of allowing all statements everywhere and relying on the interpreter to throw semantic errors, the grammar structurally prevents invalid combinations:

### 1. `topLevelElement` (Level 1)

```antlr
program: topLevelElement* EOF;

topLevelElement
    : routineDecl
    | trackDecl
    | statementGlobal
    ;
```

At the root level, only routines, tracks, and global statements are allowed. Nested tracks or routines are structurally impossible.

### 2. `statementTrack` (Level 2)

Inside a `track` definition, the parser switches to `statementTrack`:

```antlr
statementTrack
    : gridStmt
    | callExprStmt
    | ifStmtTrack
    | whileStmtTrack
    | loopStmtTrack
    | blockTrack
    | declAssignStmt
    | ...
    ;
```

Notice that `parallel` is absent from `statementTrack`. It is only present in `statementGlobal`, preventing parallel blocks from being nested inside tracks.

### 3. `gridSequence` (Level 3)

The grid block does not use standard programming statements (like variables, conditionals, or loops). Instead, it uses a specialized syntax dedicated entirely to placing musical events in time:

```antlr
gridSequence: gridSymbol+;

gridSymbol:
    gridPlayable
    | '-'
    | '~';
```

A grid can only contain notes, rests (`-`), sustain symbols (`~`), and chords. No `int x = 5` or `if (true)` can exist inside a `grid{}` block, ensuring the grid remains pure data mapped to time.

The grid sequence uses a flat list operator (`+`) instead of recursion. This makes sure that parsing very long musical grids does not cause a StackOverflow Error.

## Type Enforcement

The grammar separates primitive types from identifiers early on:

```antlr
type
    : 'int' | 'float' | 'bool' | 'string' | 'void' | 'note' | 'synth'
    ;
```
