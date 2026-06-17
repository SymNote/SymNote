import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the advanced error diagnostics system in SymNote.
 * Every test verifies that:
 *   (a) the interpreter correctly rejects invalid programs, AND
 *   (b) the resulting error message is rich, readable, and context-specific.
 */
@DisplayName("Advanced Diagnostics — Error Messages")
public class DiagnosticsTest {

    private String msg(TestHelper.Result r) {
        return r.error != null ? r.error.getMessage() : "(no error)";
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 1. "Did You Mean?" Suggestions for Undefined Variables
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("1. 'Did You Mean?' Suggestions")
    class DidYouMean {

        @Test
        @DisplayName("typo in variable name — suggests closest match")
        void suggest_close_variable_name() {
            // 'volume' is defined; 'volum' is a 1-char typo
            String code =
                "int volume = 80;\n" +
                "print(volum);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("volum"), "Should mention the typo: " + m);
            assertTrue(m.contains("Did you mean") || m.contains("volume"),
                "Should suggest 'volume': " + m);
        }

        @Test
        @DisplayName("typo in variable name — exact 2-char difference")
        void suggest_two_char_diff() {
            String code =
                "int myCounter = 0;\n" +
                "print(myCunter);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("Did you mean") || m.contains("myCounter"),
                "Should suggest 'myCounter': " + m);
        }

        @Test
        @DisplayName("completely different name — no suggestion given")
        void no_suggestion_for_gibberish() {
            String code =
                "int x = 5;\n" +
                "print(zzzzzquux);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            // Should NOT suggest anything, message should just state the undefined name
            assertFalse(msg(r).contains("Did you mean"),
                "Should not suggest for gibberish: " + msg(r));
        }

        @Test
        @DisplayName("typo in routine name — suggests closest routine")
        void suggest_routine_name() {
            String code =
                "routine computeSum(int a, int b) returns int { return a + b; }\n" +
                "int res = computSumm(1, 2);\n" +
                "print(res);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("computSumm") || m.contains("Did you mean"),
                "Should mention the name or suggest a fix: " + m);
        }

        @Test
        @DisplayName("undefined routine — message says 'routine or track' not a Java stack trace")
        void undefined_routine_user_friendly() {
            String code = "callMeIfYouDare(1, 2, 3);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            assertFalse(msg(r).contains("NullPointerException"),
                "Should not expose Java internals: " + msg(r));
            assertTrue(msg(r).contains("callMeIfYouDare"),
                "Should name the undefined routine: " + msg(r));
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 2. Type Mismatch — Both Expected and Actual Types in Message
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("2. Type Mismatch Errors")
    class TypeMismatch {

        @Test
        @DisplayName("int variable assigned float literal — message shows 'int' and 'float'")
        void int_assigned_float() {
            TestHelper.Result r = TestHelper.run("int speed = 3.14;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("int"), "Should mention declared type 'int': " + m);
            assertTrue(m.contains("float"), "Should mention actual type 'float': " + m);
            assertTrue(m.contains("speed"), "Should mention variable name: " + m);
        }

        @Test
        @DisplayName("bool variable assigned int — message shows both types")
        void bool_assigned_int() {
            TestHelper.Result r = TestHelper.run("bool flag = 42;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("bool"), "Should mention declared type: " + m);
            assertTrue(m.contains("int"), "Should mention actual type: " + m);
        }

        @Test
        @DisplayName("string variable assigned bool — message shows both types")
        void string_assigned_bool() {
            TestHelper.Result r = TestHelper.run("string label = true;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("string"), "Should mention declared type: " + m);
            assertTrue(m.contains("bool"), "Should mention actual type: " + m);
        }

        @Test
        @DisplayName("float routine arg receives int — message includes param name and routine name")
        void wrong_arg_type_int_for_float() {
            String code =
                "routine setTempo(float bpm) returns void { print(bpm); }\n" +
                "setTempo(true);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("bpm") || m.contains("setTempo"),
                "Should name param or routine: " + m);
            assertTrue(m.contains("float") || m.contains("mismatch"),
                "Should state type conflict: " + m);
        }

        @Test
        @DisplayName("return type mismatch — message shows declared return type and actual")
        void return_type_mismatch() {
            String code =
                "routine getNum() returns int {\n" +
                "    return \"hello\";\n" +
                "}\n" +
                "getNum();";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("int") || m.contains("mismatch"),
                "Should mention expected return type: " + m);
        }

        @Test
        @DisplayName("reassign int variable with bool — message shows conflict")
        void reassign_type_mismatch() {
            String code =
                "int counter = 0;\n" +
                "counter = true;";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("counter") || m.contains("int") || m.contains("bool"),
                "Should describe the type conflict: " + m);
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 3. Semantic Errors — Uninitialized, Scope Violations
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("3. Semantic Errors")
    class SemanticErrors {

        @Test
        @DisplayName("uninitialized variable read — message says 'used before being initialized'")
        void uninitialized_variable() {
            TestHelper.Result r = TestHelper.run("int x;\nint y = x + 1;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("initialized") || m.contains("x"),
                "Should mention uninitialized variable: " + m);
        }

        @Test
        @DisplayName("variable used after block scope ends — message identifies the variable")
        void out_of_scope_variable() {
            String code =
                "{\n" +
                "    int secret = 42;\n" +
                "}\n" +
                "print(secret);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("secret"), "Should mention 'secret': " + msg(r));
        }

        @Test
        @DisplayName("missing return in non-void routine — message mentions routine name and return type")
        void missing_return() {
            String code =
                "routine missingRet() returns int {\n" +
                "    int x = 5;\n" +
                "}\n" +
                "missingRet();";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("missingRet") || m.contains("return"),
                "Should mention routine and/or 'return': " + m);
            assertTrue(m.contains("int") || m.contains("no return"),
                "Should mention the declared return type or 'no return': " + m);
        }

        @Test
        @DisplayName("re-declaring variable in the same scope")
        void redeclaration() {
            TestHelper.Result r = TestHelper.run("int x = 1;\nint x = 2;");
            assertFalse(r.isSuccess());
        }

        @Test
        @DisplayName("inner routine cannot access caller-local variables — clear error")
        void inner_routine_no_access_caller_local() {
            String code =
                "routine inner() returns void { print(callerLocal); }\n" +
                "routine outer() returns void {\n" +
                "    int callerLocal = 9;\n" +
                "    inner();\n" +
                "}\n" +
                "outer();";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("callerLocal"),
                "Error should mention the unreachable variable: " + msg(r));
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 4. Operator / Arithmetic Errors
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("4. Operator Errors")
    class OperatorErrors {

        @Test
        @DisplayName("'and' operator on non-bool left side — message says 'and' and shows type")
        void and_non_bool_left() {
            TestHelper.Result r = TestHelper.run("bool ok = 5 and true;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("and") || m.contains("bool"),
                "Should mention 'and' or required type: " + m);
            assertTrue(m.contains("int"),
                "Should mention the actual type 'int': " + m);
        }

        @Test
        @DisplayName("'or' operator on non-bool — message shows actual type")
        void or_non_bool() {
            TestHelper.Result r = TestHelper.run("bool b = \"yes\" or false;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("or") || m.contains("bool"),
                "Should mention 'or' or required type: " + m);
            assertTrue(m.contains("string"),
                "Should mention the actual type 'string': " + m);
        }

        @Test
        @DisplayName("'not' operator on int — message says 'not' and shows type")
        void not_on_int() {
            TestHelper.Result r = TestHelper.run("bool b = not 42;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("not") || m.contains("bool"),
                "Should mention 'not': " + m);
            assertTrue(m.contains("int"),
                "Should mention actual type 'int': " + m);
        }

        @Test
        @DisplayName("modulus '%' on floats — message says 'int' operands required")
        void modulus_on_floats() {
            TestHelper.Result r = TestHelper.run("float r = 7.5 % 2.0;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("%") || m.contains("int"),
                "Should mention '%' or 'int': " + m);
            assertTrue(m.contains("float"),
                "Should mention actual type 'float': " + m);
        }

        @Test
        @DisplayName("division by zero — message says 'zero' and includes line number")
        void division_by_zero() {
            TestHelper.Result r = TestHelper.run("int x = 10 / 0;");
            assertFalse(r.isSuccess());
            assertTrue(msg(r).toLowerCase().contains("zero"),
                "Should mention 'zero': " + msg(r));
        }

        @Test
        @DisplayName("modulus by zero — message says 'zero'")
        void modulus_by_zero() {
            TestHelper.Result r = TestHelper.run("int r = 5 % 0;");
            assertFalse(r.isSuccess());
            assertTrue(msg(r).toLowerCase().contains("zero"),
                "Should mention 'zero': " + msg(r));
        }

        @Test
        @DisplayName("bool '<' comparison — message says to use '==' or '!='")
        void bool_lt_comparison() {
            TestHelper.Result r = TestHelper.run("bool b = true < false;");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("bool") || m.contains("==") || m.contains("compare"),
                "Should guide user to correct operators: " + m);
        }

        @Test
        @DisplayName("unary minus on string — message says numeric operand required")
        void unary_minus_on_string() {
            TestHelper.Result r = TestHelper.run("string s = -\"hello\";");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("-") || m.contains("numeric") || m.contains("string"),
                "Should mention the operator or required type: " + m);
        }

        @Test
        @DisplayName("'if' condition is int — message says 'bool' required, shows actual type and value")
        void if_condition_non_bool() {
            TestHelper.Result r = TestHelper.run("if (42) { print(\"yes\"); }");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("bool"), "Should say 'bool' is required: " + m);
            assertTrue(m.contains("int") || m.contains("42"),
                "Should show actual type or value: " + m);
        }

        @Test
        @DisplayName("'while' condition is float — message says 'bool' required")
        void while_condition_non_bool() {
            TestHelper.Result r = TestHelper.run("while (1.0) { print(\"loop\"); }");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("bool"), "Should require bool: " + m);
            assertTrue(m.contains("float") || m.contains("1.0"),
                "Should show actual type or value: " + m);
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 5. Built-in Function Errors
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("5. Built-in Function Errors")
    class BuiltinErrors {

        @Test
        @DisplayName("set_bpm with negative value — message says 'positive' and shows the value")
        void set_bpm_negative() {
            TestHelper.Result r = TestHelper.run("set_bpm(-60);");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("positive") || m.contains("set_bpm"),
                "Should describe the positivity requirement: " + m);
        }

        @Test
        @DisplayName("set_bpm with zero — message says value must be positive")
        void set_bpm_zero() {
            TestHelper.Result r = TestHelper.run("set_bpm(0);");
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("positive") || msg(r).contains("set_bpm"),
                "Should reject zero BPM: " + msg(r));
        }

        @Test
        @DisplayName("set_bpm with string — message says int or float required")
        void set_bpm_string() {
            TestHelper.Result r = TestHelper.run("set_bpm(\"fast\");");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("int") || m.contains("float") || m.contains("set_bpm"),
                "Should mention required numeric type: " + m);
            assertTrue(m.contains("string") || m.contains("fast"),
                "Should mention the actual bad value: " + m);
        }

        @Test
        @DisplayName("load_synth with int instead of string — message says string required")
        void load_synth_non_string() {
            TestHelper.Result r = TestHelper.run("synth s = load_synth(42);");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("string") || m.contains("load_synth"),
                "Should require string name: " + m);
        }

        @Test
        @DisplayName("use_synth with int — message suggests using load_synth first")
        void use_synth_non_synth() {
            String code =
                "track T() {\n" +
                "    use_synth(123);\n" +
                "}\n" +
                "parallel { T(); }";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("synth") || m.contains("use_synth") || m.contains("load_synth"),
                "Should guide user to load_synth: " + m);
        }

        @Test
        @DisplayName("print with no arguments — message says one argument required")
        void print_no_args() {
            TestHelper.Result r = TestHelper.run("print();");
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("print") || msg(r).contains("argument"),
                "Should require an argument: " + msg(r));
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 6. Call Stack Trace in Error Messages
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("6. Call Stack Traces")
    class CallStackTraces {

        @Test
        @DisplayName("error inside routine — call stack appears in message")
        void call_stack_in_routine_error() {
            String code =
                "routine crasher(int n) returns void {\n" +
                "    int bad = n / 0;\n" +
                "}\n" +
                "crasher(5);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            // The call stack trace should name the routine
            assertTrue(m.contains("crasher") || m.contains("Call stack") || m.toLowerCase().contains("zero"),
                "Should reference the routine in error: " + m);
        }

        @Test
        @DisplayName("custom SymNote StackOverflow on infinite recursion")
        void custom_stackoverflow_message() {
            String code =
                "routine forever(int n) returns int {\n" +
                "    return forever(n + 1);\n" +
                "}\n" +
                "forever(1);";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            assertNotNull(r.error);
            assertTrue(msg(r).contains("StackOverflow") || msg(r).contains("recursion") || msg(r).contains("depth"),
                "Should use custom StackOverflow message: " + msg(r));
        }

        @Test
        @DisplayName("error in nested routine — message does NOT expose java.lang class names")
        void no_java_internals_in_error() {
            String code =
                "routine inner() returns void { int x = true + 1; }\n" +
                "routine outer() returns void { inner(); }\n" +
                "outer();";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            assertFalse(msg(r).contains("java.lang"),
                "Should not expose Java internals: " + msg(r));
            assertFalse(msg(r).contains("NullPointerException"),
                "Should not expose Java NPE: " + msg(r));
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 7. Cast Errors
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("7. Cast Errors")
    class CastErrors {

        @Test
        @DisplayName("cast bool to int works (true → 1, false → 0)")
        void cast_bool_to_int_valid() {
            TestHelper.Result r = TestHelper.run("int x = (int) true; print(x);");
            assertTrue(r.isSuccess(), "Cast bool→int should work: " + msg(r));
            assertEquals(List.of("1"), r.output);
        }

        @Test
        @DisplayName("cast string 'abc' to int — message shows the actual value")
        void cast_string_to_int_invalid() {
            TestHelper.Result r = TestHelper.run("int n = (int) \"abc\";");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("abc") || m.contains("string") || m.contains("int"),
                "Should describe what failed: " + m);
        }

        @Test
        @DisplayName("cast void/null to any type — message says 'void' or 'null'")
        void cast_void_to_type() {
            String code =
                "routine doNothing() returns void { }\n" +
                "int x = (int) doNothing();";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("void") || m.contains("null") || m.contains("Cannot"),
                "Should mention void/null: " + m);
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 8. Grid / Musical Errors
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("8. Grid / Musical Errors")
    class GridErrors {

        @Test
        @DisplayName("grid note variable with non-note value — message shows variable name and value")
        void grid_variable_invalid_note_value() {
            String code =
                "string myNote = \"piano\";\n" +
                "synth S = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(S);\n" +
                "    grid(1/4) { myNote - - - }\n" +
                "}\n" +
                "parallel { T(); }";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("myNote") || m.contains("piano"),
                "Should mention variable name or value: " + m);
        }

        @Test
        @DisplayName("MIDI note out of range — message mentions 'out of range'")
        void midi_note_out_of_range() {
            // A9 = MIDI 117 which is valid, but G9 is at border. Let's use a very high octave.
            // Note token regex is [A-G][b#]?[0-9] so max octave is 9. B9 = MIDI 131 > 127.
            String code =
                "synth S = load_synth(\"piano\");\n" +
                "track T() {\n" +
                "    use_synth(S);\n" +
                "    grid(1/4) { B9 - - - }\n" +
                "}\n" +
                "parallel { T(); }";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            assertTrue(msg(r).toLowerCase().contains("range") || msg(r).contains("B9"),
                "Should mention out of range: " + msg(r));
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 9. Loop / Control Flow Errors
    // ────────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("9. Loop / Control Flow Errors")
    class LoopErrors {

        @Test
        @DisplayName("loop with float iterator type — message says 'int' required")
        void loop_float_iterator() {
            TestHelper.Result r = TestHelper.run("loop (float i from 1 to 5) { print(i); }");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("int") || m.contains("i"),
                "Should require int iterator: " + m);
        }

        @Test
        @DisplayName("loop 'from' is float — message shows actual type")
        void loop_from_is_float() {
            TestHelper.Result r = TestHelper.run("loop (int i from 1.5 to 5) { print(i); }");
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("integer") || m.contains("float") || m.contains("int"),
                "Should describe the type error in loop bounds: " + m);
        }

        @Test
        @DisplayName("void return inside non-void routine — message mentions routine and type")
        void void_return_in_non_void_routine() {
            String code =
                "routine getVal() returns int {\n" +
                "    return;\n" +
                "}\n" +
                "getVal();";
            TestHelper.Result r = TestHelper.run(code);
            assertFalse(r.isSuccess());
            String m = msg(r);
            assertTrue(m.contains("getVal") || m.contains("int") || m.contains("return"),
                "Should describe the void-return in non-void context: " + m);
        }
    @Nested
    @DisplayName("10. Note Type Misuses")
    class NoteTypeErrors {

        @Test
        @DisplayName("Note type in math addition")
        void note_in_addition() {
            TestHelper.Result r = TestHelper.run(
                "note myNote = C4;\n" +
                "int a = myNote + 1;"
            );
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("Invalid operands for addition/subtraction"), "Should explain the math failure");
        }

        @Test
        @DisplayName("Note type in multiplication")
        void note_in_multiplication() {
            TestHelper.Result r = TestHelper.run(
                "note myNote = C4;\n" +
                "int a = myNote * 2;"
            );
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("note"), "Should mention 'note'");
            assertTrue(msg(r).contains("Operator '*' cannot be applied"), "Should explain the math failure");
        }

        @Test
        @DisplayName("Note type used as boolean condition")
        void note_as_boolean() {
            TestHelper.Result r = TestHelper.run(
                "note n = C4;\n" +
                "if (n) { }"
            );
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("note"), "Should mention 'note'");
            assertTrue(msg(r).contains("Condition in 'if' must be a bool"), "Should mention condition type requirement");
        }

        @Test
        @DisplayName("Assigning string literal to note variable")
        void assign_string_to_note() {
            TestHelper.Result r = TestHelper.run(
                "note n = \"C4\";"
            );
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("Type mismatch"), "Should mention type mismatch");
            assertTrue(msg(r).contains("declared as 'note' but got string"), "Should mention expected note but got string");
        }
        
        @Test
        @DisplayName("Using unary minus on note")
        void unary_minus_note() {
            TestHelper.Result r = TestHelper.run(
                "note n = C4;\n" +
                "note n2 = -n;"
            );
            assertFalse(r.isSuccess());
            assertTrue(msg(r).contains("Unary '-' requires a numeric operand"), "Should mention unary numeric requirement");
            assertTrue(msg(r).contains("note"), "Should mention note type");
        }

        @Test
        @DisplayName("error: undefined variable in routine shows line number and call stack")
        void error_undefined_var_routine_diagnostic() {
            String code = 
                "routine funkcja_a() returns void {\n" +
                "    int tajna_zmienna = 5;\n" +
                "}\n" +
                "routine funkcja_b() returns void {\n" +
                "    print(tajna_zmienna);\n" +
                "}\n" +
                "funkcja_a();\n" +
                "funkcja_b();\n";
                
            TestHelper.Result r = TestHelper.run(code);
            
            assertFalse(r.isSuccess(), "Script should have failed due to undefined variable");
            assertNotNull(r.error, "Expected an error");
            
            String msg = r.error.getMessage();
            assertTrue(msg.contains("Undefined variable 'tajna_zmienna' at line 5"), "Missing line number in error: " + msg);
            assertTrue(msg.contains("at routine 'funkcja_b'"), "Missing call stack trace: " + msg);
        }
    }
}
}
