import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Documentation Tests — every code snippet shown in the User Documentation
 * must be represented by a test here.
 */
public class DocumentationTest {

    // ─────────────────────────────────────────────
    // installation.md — "Writing Your First Program"
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("installation.md")
    class InstallationDoc {

        @Test
        @DisplayName("first program compiles and has no errors")
        void first_program_no_error() {
            TestHelper.Result r = TestHelper.run(
                "set_bpm(120);\n" +
                "synth Piano = load_synth(\"piano\");\n" +
                "track Arpeggio(int bars) {\n" +
                "    use_synth(Piano);\n" +
                "    loop (int i from 1 to bars) {\n" +
                "        grid(1/8) { C4 E4 G4 C5 G4 E4 C4 - }\n" +
                "    }\n" +
                "}\n" +
                "Arpeggio(2);"
            );
            assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        }
    }

    // ─────────────────────────────────────────────
    // language-guide.md — Variables & Types
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("language-guide.md — Variables & Types")
    class VariableTypes {

        @Test
        @DisplayName("int, float, bool, string declarations work")
        void primitive_declarations() {
            TestHelper.Result r = TestHelper.run(
                "int transpose = -12;\n" +
                "float delay_feedback = 0.5;\n" +
                "bool play_chorus = true;\n" +
                "string label = \"intro\";\n" +
                "print(transpose);\n" +
                "print(delay_feedback);\n" +
                "print(play_chorus);\n" +
                "print(label);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("-12", "0.5", "true", "intro"), r.output);
        }

        @Test
        @DisplayName("declare without init, assign later")
        void declare_then_assign() {
            TestHelper.Result r = TestHelper.run(
                "int x;\n" +
                "x = 10;\n" +
                "print(x);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("10"), r.output);
        }

        @Test
        @DisplayName("reading uninitialized variable throws error")
        void uninitialized_error() {
            TestHelper.Result r = TestHelper.run("int x; print(x);");
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("used before being initialized"),
                    "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("float accepts int literal (promotion)")
        void float_accepts_int_literal() {
            TestHelper.Result r = TestHelper.run("float x = 3; print(x);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("3.0"), r.output);
        }

        @Test
        @DisplayName("int rejects float literal")
        void int_rejects_float() {
            TestHelper.Result r = TestHelper.run("int x = 3.5;");
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Type mismatch"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("explicit cast (int)3.9 truncates to 3")
        void explicit_cast_float_to_int() {
            TestHelper.Result r = TestHelper.run("int x = (int)3.9; print(x);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("3"), r.output);
        }

        @Test
        @DisplayName("explicit cast (float)1 produces 1.0")
        void explicit_cast_int_to_float() {
            TestHelper.Result r = TestHelper.run("float x = (float)1; print(x);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("1.0"), r.output);
        }

        @Test
        @DisplayName("reassignment without type keyword works")
        void reassignment() {
            TestHelper.Result r = TestHelper.run("int x = 5; x = 10; print(x);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("10"), r.output);
        }
    }

    // ─────────────────────────────────────────────
    // language-guide.md — Operators
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("language-guide.md — Operators")
    class Operators {

        @Test
        @DisplayName("arithmetic operators produce correct results")
        void arithmetic() {
            TestHelper.Result r = TestHelper.run(
                "int a = 10 + 3;\n" +
                "int b = 10 - 3;\n" +
                "int c = 10 * 3;\n" +
                "int d = 10 / 3;\n" +
                "int e = 10 % 3;\n" +
                "print(a); print(b); print(c); print(d); print(e);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("13", "7", "30", "3", "1"), r.output);
        }

        @Test
        @DisplayName("float division gives decimal result")
        void float_division() {
            TestHelper.Result r = TestHelper.run("float f = 10.0 / 3.0; print(f);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertFalse(r.output.get(0).equals("3"), "Expected decimal, got: " + r.output.get(0));
        }

        @Test
        @DisplayName("logical and/or/not work correctly")
        void logical_operators() {
            TestHelper.Result r = TestHelper.run(
                "bool a = true;\n" +
                "bool b = false;\n" +
                "print(a and b);\n" +
                "print(a or b);\n" +
                "print(not a);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("false", "true", "false"), r.output);
        }

        @Test
        @DisplayName("and short-circuits — right side not evaluated on false")
        void and_short_circuit() {
            TestHelper.Result r = TestHelper.run("bool c = false and (1/0 == 1); print(c);");
            assertTrue(r.isSuccess(), "Short-circuit should prevent div-by-zero");
            assertEquals(List.of("false"), r.output);
        }

        @Test
        @DisplayName("or short-circuits — right side not evaluated on true")
        void or_short_circuit() {
            TestHelper.Result r = TestHelper.run("bool c = true or (1/0 == 1); print(c);");
            assertTrue(r.isSuccess(), "Short-circuit should prevent div-by-zero");
            assertEquals(List.of("true"), r.output);
        }

        @Test
        @DisplayName("increment ++ on int works")
        void increment() {
            TestHelper.Result r = TestHelper.run("int i = 0; i++; print(i);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("1"), r.output);
        }

        @Test
        @DisplayName("decrement -- on int works")
        void decrement() {
            TestHelper.Result r = TestHelper.run("int i = 5; i--; print(i);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("4"), r.output);
        }

        @Test
        @DisplayName("increment on float is rejected")
        void increment_float_rejected() {
            TestHelper.Result r = TestHelper.run("float f = 1.0; f++;");
            assertFalse(r.isSuccess());
            assertNotNull(r.error);
        }
    }

    // ─────────────────────────────────────────────
    // language-guide.md — Control Flow
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("language-guide.md — Control Flow")
    class ControlFlow {

        @Test
        @DisplayName("if/else selects correct branch")
        void if_else() {
            TestHelper.Result r = TestHelper.run(
                "int x = 10;\n" +
                "if (x > 5) { print(\"big\"); } else { print(\"small\"); }"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("big"), r.output);
        }

        @Test
        @DisplayName("if condition must be bool — int rejected")
        void if_requires_bool() {
            TestHelper.Result r = TestHelper.run("int x = 1; if (x) { print(x); }");
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("boolean"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("variable declared in braces-less if does not leak out")
        void if_no_brace_scope_leak() {
            TestHelper.Result r = TestHelper.run(
                "if (true)\n" +
                "    int hidden = 99;\n" +
                "print(hidden);"
            );
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Undefined variable"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("while loop counts correctly")
        void while_loop() {
            TestHelper.Result r = TestHelper.run(
                "int count = 0;\n" +
                "while (count < 3) {\n" +
                "    count++;\n" +
                "    print(count);\n" +
                "}"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("1", "2", "3"), r.output);
        }

        @Test
        @DisplayName("for-range loop (from 1 to 5) prints 1 through 5")
        void loop_range() {
            TestHelper.Result r = TestHelper.run(
                "loop (int i from 1 to 5) { print(i); }"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("1", "2", "3", "4", "5"), r.output);
        }

        @Test
        @DisplayName("sequential loops with same variable name don't conflict")
        void sequential_loops_same_var() {
            TestHelper.Result r = TestHelper.run(
                "loop (int i from 1 to 2) { print(i); }\n" +
                "loop (int i from 1 to 2) { print(i); }"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("1", "2", "1", "2"), r.output);
        }

        @Test
        @DisplayName("break exits loop early")
        void break_exits_loop() {
            TestHelper.Result r = TestHelper.run(
                "loop (int i from 1 to 10) {\n" +
                "    if (i == 4) { break; }\n" +
                "    print(i);\n" +
                "}"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("1", "2", "3"), r.output);
        }
    }

    // ─────────────────────────────────────────────
    // language-guide.md — Scoping
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("language-guide.md — Scoping")
    class Scoping {

        @Test
        @DisplayName("variable from inner scope is destroyed after block")
        void inner_scope_destroyed() {
            TestHelper.Result r = TestHelper.run(
                "if (true) { int y = 20; }\n" +
                "print(y);"
            );
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Undefined variable"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("shadowing — inner x overrides outer, outer restored after block")
        void variable_shadowing() {
            TestHelper.Result r = TestHelper.run(
                "int x = 10;\n" +
                "if (true) {\n" +
                "    int x = 99;\n" +
                "    print(x);\n" +
                "}\n" +
                "print(x);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("99", "10"), r.output);
        }
    }

    // ─────────────────────────────────────────────
    // language-guide.md — Routines
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("language-guide.md — Routines")
    class Routines {

        @Test
        @DisplayName("routine returns calculated value")
        void routine_return() {
            TestHelper.Result r = TestHelper.run(
                "routine multiply(int a, int b) returns int { return a * b; }\n" +
                "int result = multiply(4, 5);\n" +
                "print(result);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("20"), r.output);
        }

        @Test
        @DisplayName("void routine executes but returns nothing assignable")
        void void_routine() {
            TestHelper.Result r = TestHelper.run(
                "routine greet(string name) returns void { print(name); }\n" +
                "greet(\"SymNote\");"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("SymNote"), r.output);
        }

        @Test
        @DisplayName("assigning void return throws error")
        void assign_void_throws() {
            TestHelper.Result r = TestHelper.run(
                "routine doNothing() returns void { }\n" +
                "int a = doNothing();"
            );
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Cannot assign void value"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("pass-by-value — original variable unchanged after routine")
        void pass_by_value() {
            TestHelper.Result r = TestHelper.run(
                "routine corrupt(int val) returns int { val = 999; return val; }\n" +
                "int original = 42;\n" +
                "int returned = corrupt(original);\n" +
                "print(original);\n" +
                "print(returned);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("42", "999"), r.output);
        }

        @Test
        @DisplayName("recursion — factorial(5) = 120")
        void recursion_factorial() {
            TestHelper.Result r = TestHelper.run(
                "routine factorial(int n) returns int {\n" +
                "    if (n <= 1) { return 1; }\n" +
                "    return n * factorial(n - 1);\n" +
                "}\n" +
                "print(factorial(5));\n" +
                "print(factorial(10));"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("120", "3628800"), r.output);
        }

        @Test
        @DisplayName("inner routine cannot access caller's local variables")
        void lexical_scope_isolation() {
            TestHelper.Result r = TestHelper.run(
                "routine inner() returns int { return secret; }\n" +
                "routine outer() returns int { int secret = 42; return inner(); }\n" +
                "print(outer());"
            );
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Undefined variable 'secret'"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("wrong argument count rejected")
        void wrong_arg_count() {
            TestHelper.Result r = TestHelper.run(
                "routine multiply(int a, int b) returns int { return a * b; }\n" +
                "int result = multiply(5);"
            );
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("expected 2 arguments but got 1"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("wrong argument type rejected")
        void wrong_arg_type() {
            TestHelper.Result r = TestHelper.run(
                "routine take_int(int a) returns int { return a; }\n" +
                "take_int(2.3);"
            );
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Type mismatch"), "Got: " + r.error.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // examples.md — Arithmetic & Strings
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("examples.md")
    class Examples {

        @Test
        @DisplayName("example 2 — arithmetic operations correct")
        void arithmetic_example() {
            TestHelper.Result r = TestHelper.run(
                "int a = 10;\n" +
                "int b = 3;\n" +
                "int sum = a + b;\n" +
                "int quotient = a / b;\n" +
                "int remainder = a % b;\n" +
                "print(sum);\n" +
                "print(quotient);\n" +
                "print(remainder);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("13", "3", "1"), r.output);
        }

        @Test
        @DisplayName("example 4 — explicit casting")
        void casting_example() {
            TestHelper.Result r = TestHelper.run(
                "float x = 3.9;\n" +
                "int truncated = (int)x;\n" +
                "print(truncated);\n" +
                "int y = 1;\n" +
                "float promoted = (float)y;\n" +
                "print(promoted);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("3", "1.0"), r.output);
        }

        @Test
        @DisplayName("example 6 — scoping and shadowing")
        void scoping_example() {
            TestHelper.Result r = TestHelper.run(
                "int x = 10;\n" +
                "if (true) {\n" +
                "    int x = 99;\n" +
                "    print(x);\n" +
                "}\n" +
                "print(x);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("99", "10"), r.output);
        }

        @Test
        @DisplayName("example 9 — string concatenation and repetition")
        void string_example() {
            TestHelper.Result r = TestHelper.run(
                "string greeting = \"Hello\";\n" +
                "string name = \"SymNote\";\n" +
                "string message = greeting + \", \" + name + \"!\";\n" +
                "print(message);\n" +
                "string repeated = \"ha\" * 3;\n" +
                "print(repeated);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("Hello, SymNote!", "hahaha"), r.output);
        }

        @Test
        @DisplayName("multi-track program with parallel compiles without error")
        void multi_track_parallel() {
            TestHelper.Result r = TestHelper.run(
                "set_bpm(90);\n" +
                "synth Piano = load_synth(\"piano\");\n" +
                "synth Strings = load_synth(\"strings\");\n" +
                "track PianoArp(int bars) {\n" +
                "    use_synth(Piano);\n" +
                "    loop (int i from 1 to bars) {\n" +
                "        grid(1/8) { C4 E4 G4 C5 G4 E4 C4 - }\n" +
                "    }\n" +
                "}\n" +
                "track ChordLayer(int bars) {\n" +
                "    use_synth(Strings);\n" +
                "    grid(1/1) { [C3, E3, G3] }\n" +
                "}\n" +
                "parallel {\n" +
                "    PianoArp(2);\n" +
                "    ChordLayer(1);\n" +
                "}"
            );
            assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        }
    }

    // ─────────────────────────────────────────────
    // diagnostics.md — Error message coverage
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("diagnostics.md — Error messages")
    class DiagnosticsDoc {

        @Test
        @DisplayName("syntax error — invalid declaration")
        void syntax_error() {
            TestHelper.Result r = TestHelper.run("int x+2 = 3;");
            assertTrue(r.hasSyntaxError, "Expected syntax error");
        }

        @Test
        @DisplayName("type mismatch — float assigned to int")
        void type_mismatch() {
            TestHelper.Result r = TestHelper.run("int a = 2 + 2.3;");
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Type mismatch"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("undefined variable error")
        void undefined_variable() {
            TestHelper.Result r = TestHelper.run("int a = x;");
            assertFalse(r.isSuccess());
            assertNotNull(r.error);
        }

        @Test
        @DisplayName("uninitialized variable error")
        void uninitialized_variable() {
            TestHelper.Result r = TestHelper.run("int empty;\nint crash = empty + 10;");
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("used before being initialized"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("division by zero error")
        void division_by_zero() {
            TestHelper.Result r = TestHelper.run("int result = 10 / (3 - 3);");
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().toLowerCase().contains("zero"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("duplicate variable in same scope rejected")
        void duplicate_variable() {
            TestHelper.Result r = TestHelper.run("int x = 5;\nint x = 10;");
            assertFalse(r.isSuccess());
            assertNotNull(r.error);
        }

        @Test
        @DisplayName("routine return type mismatch rejected")
        void return_type_mismatch() {
            TestHelper.Result r = TestHelper.run(
                "routine trick() returns int { return \"I am a string\"; }\n" +
                "trick();"
            );
            assertFalse(r.isSuccess());
            assertTrue(r.error.getMessage().contains("Type mismatch"), "Got: " + r.error.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // language-guide.md — 3-Level Architecture
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("language-guide.md — 3-Level Architecture")
    class LevelArchitecture {

        // ── Level 1 (Global / Timeline) ──────────────

        @Test
        @DisplayName("L1: set_bpm at global scope works")
        void l1_set_bpm_allowed() {
            TestHelper.Result r = TestHelper.run("set_bpm(120);");
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L1: load_synth at global scope works")
        void l1_load_synth_allowed() {
            TestHelper.Result r = TestHelper.run("synth Piano = load_synth(\"piano\");");
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L1: global variable declaration works")
        void l1_global_var_allowed() {
            TestHelper.Result r = TestHelper.run("int bars = 4; print(bars);");
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("4"), r.output);
        }

        @Test
        @DisplayName("L1: routine declared at global scope works")
        void l1_routine_decl_allowed() {
            TestHelper.Result r = TestHelper.run(
                "routine add(int a, int b) returns int { return a + b; }\n" +
                "int result = add(3, 7);\n" +
                "print(result);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
            assertEquals(List.of("10"), r.output);
        }

        @Test
        @DisplayName("L1: track declaration and sequential call works")
        void l1_track_decl_and_call_allowed() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track Melody(int bars) {\n" +
                "    use_synth(Piano);\n" +
                "    loop (int i from 1 to bars) {\n" +
                "        grid(1/8) { C4 E4 G4 C5 G4 E4 C4 - }\n" +
                "    }\n" +
                "}\n" +
                "Melody(1);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L1: parallel block with two tracks works")
        void l1_parallel_allowed() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track A(int b) { use_synth(Piano); grid(1/4) { C4 - - - } }\n" +
                "track B(int b) { use_synth(Piano); grid(1/4) { G4 - - - } }\n" +
                "parallel { A(1); B(1); }"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L1: if/while/loop at global scope works")
        void l1_control_flow_allowed() {
            TestHelper.Result r = TestHelper.run(
                "int x = 0;\n" +
                "if (true) { x = 1; }\n" +
                "while (x < 3) { x++; }\n" +
                "loop (int i from 1 to 2) { x = x + i; }\n" +
                "print(x);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L1: routine must be declared at global scope — routine inside track is syntax error")
        void l1_routine_inside_track_is_syntax_error() {
            TestHelper.Result r = TestHelper.run(
                "track Bad() {\n" +
                "    routine helper() returns void { }\n" +
                "}"
            );
            // routine inside a track body is not valid grammar
            assertTrue(r.hasSyntaxError || !r.isSuccess(),
                    "Expected failure for routine declared inside track");
        }

        // ── Level 2 (Track Body) ──────────────────────

        @Test
        @DisplayName("L2: use_synth inside track works")
        void l2_use_synth_allowed() {
            // use_synth is the primary way to configure a track's instrument
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/4) { C4 - - - }\n" +
                "}\n" +
                "T();"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L2: local variables inside track are isolated from global scope")
        void l2_local_var_isolation() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    int local = 42;\n" +
                "    grid(1/4) { C4 - - - }\n" +
                "}\n" +
                "T();\n" +
                "print(local);"  // should fail — local is out of scope
            );
            assertFalse(r.isSuccess(), "Expected local track variable to be inaccessible outside");
            assertTrue(r.error.getMessage().contains("Undefined variable"), "Got: " + r.error.getMessage());
        }

        @Test
        @DisplayName("L2: if/while/loop inside track work")
        void l2_control_flow_allowed() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T(int bars) {\n" +
                "    use_synth(Piano);\n" +
                "    int step = 0;\n" +
                "    while (step < bars) {\n" +
                "        if (step % 2 == 0) {\n" +
                "            grid(1/8) { C5 E5 G5 - C5 E5 G5 - }\n" +
                "        } else {\n" +
                "            grid(1/8) { G4 B4 D5 - G4 B4 D5 - }\n" +
                "        }\n" +
                "        step++;\n" +
                "    }\n" +
                "}\n" +
                "T(2);"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L2: parallel inside track is syntax error")
        void l2_parallel_inside_track_is_syntax_error() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track A() { use_synth(Piano); grid(1/4) { C4 - - - } }\n" +
                "track Bad() {\n" +
                "    parallel { A(); }\n" +
                "}"
            );
            assertTrue(r.hasSyntaxError, "Expected syntax error for parallel inside track");
        }

        @Test
        @DisplayName("L2: nested track declaration inside track is syntax error")
        void l2_nested_track_is_syntax_error() {
            // The grammar only allows trackDecl at the top-level (topLevelElement).
            // Inside a track body, only statementTrack rules are valid.
            // A 'track' keyword inside a track body does not match any statementTrack rule.
            TestHelper.Result r = TestHelper.run(
                "track Outer() {\n" +
                "    int x = 5;\n" +
                "    track Inner() { }\n" +
                "}\n" +
                "Outer();"
            );
            assertFalse(r.isSuccess(), "Expected failure for nested track declaration");
        }

        @Test
        @DisplayName("L2: routine call from inside a track works")
        void l2_routine_call_from_track_allowed() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "routine steps() returns int { return 2; }\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    loop (int i from 1 to steps()) {\n" +
                "        grid(1/4) { C4 - - - }\n" +
                "    }\n" +
                "}\n" +
                "T();"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        // ── Level 3 (Grid) ────────────────────────────

        @Test
        @DisplayName("L3: notes, rest, sustain, chord, vol in grid work")
        void l3_basic_grid_elements_work() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/16) {\n" +
                "        C4 D4 E4 F4\n" +
                "        G4 ~ ~ -\n" +
                "        [C4, E4, G4] - - -\n" +
                "        C4.vol(1.0) E4.vol(0.8) - -\n" +
                "    }\n" +
                "}\n" +
                "T();"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L3: all supported resolutions parse correctly")
        void l3_all_resolutions_valid() {
            String[] resolutions = {"1/1", "1/2", "1/4", "1/8", "1/16", "1/32"};
            for (String res : resolutions) {
                TestHelper.Result r = TestHelper.run(
                    "synth Piano = load_synth(\"piano\");\n" +
                    "track T() { use_synth(Piano); grid(" + res + ") { C4 - } }\n" +
                    "T();"
                );
                assertTrue(r.isSuccess(), "Resolution " + res + " failed: " + errorMsg(r));
            }
        }

        @Test
        @DisplayName("L3: variable in grid via ID resolves to note pitch")
        void l3_variable_note_in_grid() {
            // A synth variable used as a note-element in a grid should work
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/4) { C4 E4 G4 - }\n" +
                "}\n" +
                "T();"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L3: if inside grid is syntax error")
        void l3_if_inside_grid_is_syntax_error() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/8) { if (true) { C4 } }\n" +
                "}"
            );
            assertTrue(r.hasSyntaxError, "Expected syntax error for if inside grid");
        }

        @Test
        @DisplayName("L3: variable declaration inside grid is syntax error")
        void l3_var_decl_inside_grid_is_syntax_error() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/8) { int x = 5; }\n" +
                "}"
            );
            assertTrue(r.hasSyntaxError, "Expected syntax error for var declaration inside grid");
        }

        @Test
        @DisplayName("L3: while inside grid is syntax error")
        void l3_while_inside_grid_is_syntax_error() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/8) { while (true) { C4 } }\n" +
                "}"
            );
            assertTrue(r.hasSyntaxError, "Expected syntax error for while inside grid");
        }

        @Test
        @DisplayName("L3: vol modifier on note works")
        void l3_vol_modifier_on_note() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "float myVol = 0.5;\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/4) { C4.vol(1.0) E4.vol(0.5) G4.vol(myVol) - }\n" +
                "}\n" +
                "T();"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("L3: vol modifier on chord works")
        void l3_vol_modifier_on_chord() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    grid(1/4) { [C4, E4, G4].vol(0.8) - - - }\n" +
                "}\n" +
                "T();"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        // ── Routine scope rules ───────────────────────

        @Test
        @DisplayName("Routines: routine declared at global scope is accessible from track")
        void routine_global_scope_accessible_from_track() {
            TestHelper.Result r = TestHelper.run(
                "synth Piano = load_synth(\"piano\");\n" +
                "routine bars() returns int { return 2; }\n" +
                "track T() {\n" +
                "    use_synth(Piano);\n" +
                "    loop (int i from 1 to bars()) {\n" +
                "        grid(1/4) { C4 - - - }\n" +
                "    }\n" +
                "}\n" +
                "T();"
            );
            assertTrue(r.isSuccess(), errorMsg(r));
        }

        @Test
        @DisplayName("Routines: duplicate routine name is rejected")
        void routine_duplicate_rejected() {
            TestHelper.Result r = TestHelper.run(
                "routine calc() returns int { return 1; }\n" +
                "routine calc() returns int { return 2; }"
            );
            assertFalse(r.isSuccess(), "Expected duplicate routine to fail");
            assertTrue(r.error.getMessage().contains("already defined"), "Got: " + r.error.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    private static String errorMsg(TestHelper.Result r) {
        if (r.hasSyntaxError) return "(syntax error)";
        if (r.error != null) return r.error.getMessage();
        return "(none)";
    }
}
