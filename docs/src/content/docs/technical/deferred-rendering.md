---
title: Deferred Rendering
description: Why audio rendering is separated from AST evaluation.
sidebar:
  order: 5
---

## The Two-Stage Approach

The SymNote execution behaves in two distinct stages:

1. **AST Traversal**
2. **Audio Rendering**

### Why Defer Rendering?

Generating audio requires precise timeline mapping, concurrent tracks, and perfect synchronization. 

If the interpreter processed the audio directly while evaluating the tree:
- Handling overlapping tracks (`parallel` statements) would require multithreading during AST traversal, massively increasing complexity and race conditions.

### Implementation

To solve this, SymNote uses `SymNoteTimeline`, `FlowExecutor`, and `GridExecutor`. 

When the interpreter encounters a musical instruction (like a note or a grid block), it delegates the instruction to an executor, which registers the event onto the `SymNoteTimeline` with a calculated tick offset.

Only after the entire program has been successfully traversed without semantic errors does the Java main class trigger the actual `JavaMidiRenderer` or `AudioRenderer`. The renderer consumes the complete `SymNoteTimeline` and renders audio in sync.
