---
title: Language Principles
description: The core logic behind SymNote.
---

This document defines the high-level logic and architectural philosophy of SymNote.


## 1. Execution Model: AST-Based Interpretation

SymNote follows an interpreter architecture powered by ANTLR4. The system processes source code through a multi-stage pipeline:

1. **Lexical & Syntactic Analysis**: ANTLR4 generates a concrete parse tree from the source code.
2. **AST Transformation**: The parse tree is refined into an **Abstract Syntax Tree (AST)**, which serves as a simplified, high-level representation of the program logic.
3. **Visitor Pattern Evaluation**: The interpreter utilizes the **Visitor pattern** to traverse the AST. Unlike the Listener pattern, the Visitor provides explicit control over the traversal, allowing the interpreter to handle complex control flows such as conditional branches (`if`), repetitive iterations (`loop`), and recursive function calls by selectively visiting specific subtrees.

## 2. Deterministic Timing

* **BPM Dominance**: All time advancements in `grid` blocks are calculated relative to the engine's internal `BPM` setting.
* **Atomic Advancement**: Operations between time-advancing symbols (like `-` or `~`) are treated as taking place at the same logical time instant, ensuring sample-accurate synchronization between parallel tracks.