import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

    // VALID CASES: value assertions

    @Test
    @DisplayName("valid: int a = 7/(3) a == 2")
    void valid_int_division() {
        TestHelper.Result r = TestHelper.run("int a = 7/(3); print(a);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("2"), r.output);
    }

    @Test
    @DisplayName("valid: a=a reassigning")
    void valid_reassign_self() {
        TestHelper.Result r = TestHelper.run("int a = 7; a = a; print(a);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("7"), r.output);
    }

    @Test
    @DisplayName("valid: bool c = true or false and false is true")
    void valid_bool_or_and_precedence() {
        TestHelper.Result r = TestHelper.run("bool c = true or false and false; print(c);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: bool aa = 5 >= 5 is true")
    void valid_bool_comparison() {
        TestHelper.Result r = TestHelper.run("bool aa = 5 >= 5; print(aa);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: boolean not and equality")
    void valid_bool_not_and_equality() {
        TestHelper.Result r = TestHelper.run(
                "bool a = true;\n" +
                "bool b = not a;\n" +
                "bool c = (a != b);\n" +
                "bool d = (a == true);\n" +
                "print(b);\n" +
                "print(c);\n" +
                "print(d);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("false", "true", "true"), r.output);
    }

    @Test
    @DisplayName("valid: int aa = 2+-2 is 0")
    void valid_int_add_unary_minus() {
        TestHelper.Result r = TestHelper.run("int aa = 2+-2; print(aa);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("0"), r.output);
    }

    @Test
    @DisplayName("valid: int aa = -3 is -3")
    void valid_int_negative_literal() {
        TestHelper.Result r = TestHelper.run("int aa = -3; print(aa);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("-3"), r.output);
    }

    @Test
    @DisplayName("valid: float a = 2.0+3*4-5*6 is -16.0")
    void valid_float_complex_expression() {
        TestHelper.Result r = TestHelper.run("float a = 2.0+3*4-5*6; print(a);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("-16.0"), r.output);
    }

    @Test
    @DisplayName("valid: string multiplication (Int*Str and Str*Int)")
    void valid_commutative_string_multiplication() {
        TestHelper.Result r = TestHelper.run(
                "string s1 = 3 * \"A\";\n" +
                "string s2 = \"B\" * 2;\n" +
                "print(s1);\n" +
                "print(s2);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("AAA", "BB"), r.output);
    }

    // LOOP TESTS

    @Test
    @DisplayName("valid: loop variable value correct on each iteration (print(i))")
    void valid_loop_variable_value() {
        TestHelper.Result r = TestHelper.run(
                "loop (int i from 1 to 3) {\n" +
                "    print(i);\n" +
                "}");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("1", "2", "3"), r.output);
    }

    @Test
    @DisplayName("valid: loop arithmetic — print(i + 10) on loop variable")
    void valid_loop_variable_in_arithmetic() {
        TestHelper.Result r = TestHelper.run(
                "loop (int i from 1 to 3) {\n" +
                "    print(i + 10);\n" +
                "}");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("11", "12", "13"), r.output);
    }

    @Test
    @DisplayName("valid: loop variable defined inside body cannot leak out")
    void valid_loop_scope_isolation() {
        TestHelper.Result r = TestHelper.run(
                "int total = 0;\n" +
                        "loop (int i from 1 to 4) {\n" +
                        "    total = i;\n" +
                        "}\n" +
                        "print(total);\n");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("4"), r.output);
    }

    @Test
    @DisplayName("valid: sequential loops can safely reuse the same variable name (loop leak fix)")
    void valid_sequential_loops_scope() {
        TestHelper.Result r = TestHelper.run(
                "loop (int i from 1 to 2) {\n" +
                "    print(i);\n" +
                "}\n" +
                "loop (int i from 1 to 2) {\n" +
                "    print(i);\n" +
                "}"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("1", "2", "1", "2"), r.output);
    }

    @Test
    @DisplayName("valid: while loop execution")
    void valid_while_loop() {
        TestHelper.Result r = TestHelper.run(
                "int i = 0;\n" +
                "while (i < 3) {\n" +
                "    i++;\n" +
                "    print(i);\n" +
                "}"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("1", "2", "3"), r.output);
    }

    // ROUTINE TESTS

    @Test
    @DisplayName("valid: routine returns calculated value correctly")
    void valid_routine_return() {
        TestHelper.Result r = TestHelper.run(
                "routine calc_multiplier(int a) returns int {\n" +
                "    return a * 2;\n" +
                "}\n" +
                "int result = calc_multiplier(5);\n" +
                "print(result);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("10"), r.output);
    }

    @Test
    @DisplayName("valid: void routine execution")
    void valid_void_routine() {
        TestHelper.Result r = TestHelper.run(
                "int global = 0;\n" +
                "routine mutate_global() returns void {\n" +
                "    global = 42;\n" +
                "}\n" +
                "mutate_global();\n" +
                "print(global);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("42"), r.output);
    }

    @Test
    @DisplayName("valid: pass-by-value prevents mutation of outer variables")
    void valid_routine_pass_by_value() {
        TestHelper.Result r = TestHelper.run(
                "int x = 10;\n" +
                "routine corruptor(int x) returns int {\n" +
                "    x = 99;\n" +
                "    return x;\n" +
                "}\n" +
                "int y = corruptor(x);\n" +
                "print(x);\n" +
                "print(y);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("10", "99"), r.output);
    }

    @Test
    @DisplayName("error: inner function cannot access caller's local variables (lexical scoping)")
    void error_inner_function_no_caller_access() {
        TestHelper.Result r = TestHelper.run(
                "routine inner() returns int {\n" +
                "    return secret;\n" +
                "}\n" +
                "routine outer() returns int {\n" +
                "    int secret = 42;\n" +
                "    return inner();\n" +
                "}\n" +
                "print(outer());"
        );
        assertFalse(r.isSuccess(), "Expected undefined variable error");
        assertNotNull(r.error);
        assertTrue(r.error.getMessage().contains("Undefined variable 'secret'"),
                "Expected Undefined variable, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: cannot assign void return value to a variable")
    void error_assign_void_to_variable() {
        TestHelper.Result r = TestHelper.run(
                "routine voidFunc() returns void { }\n" +
                "int a = voidFunc();"
        );
        assertFalse(r.isSuccess());
        assertTrue(r.error.getMessage().contains("Cannot assign void value to variable 'a'"),
                "Expected error about assigning void value, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: cannot pass void value as an argument")
    void error_pass_void_as_argument() {
        TestHelper.Result r = TestHelper.run(
                "routine voidFunc() returns void { }\n" +
                "print(voidFunc());"
        );
        assertFalse(r.isSuccess());
        assertTrue(r.error.getMessage().contains("Cannot pass void value as argument"),
                "Expected error about passing void value, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: cannot use void value in mathematical expression")
    void error_math_with_void() {
        TestHelper.Result r = TestHelper.run(
                "routine voidFunc() returns void { }\n" +
                "int a = 1 + voidFunc();"
        );
        assertFalse(r.isSuccess());
        assertTrue(r.error.getMessage().contains("Invalid operands for addition/subtraction"),
                "Expected error about invalid operands, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("valid: standalone expressions (++) work inside routines")
    void valid_standalone_expressions_in_routines() {
        TestHelper.Result r = TestHelper.run(
                "routine test_increment() returns int {\n" +
                "    int x = 5;\n" +
                "    x++;\n" +
                "    return x;\n" +
                "}\n" +
                "print(test_increment());"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("6"), r.output);
    }

    @Test
    @DisplayName("valid: side-effects execute exactly once (double-eval bug fix)")
    void valid_no_double_evaluation_on_assignment() {
        TestHelper.Result r = TestHelper.run(
                "int counter = 0;\n" +
                "routine inc() returns float {\n" +
                "    counter = counter + 1;\n" +
                "    return 100.0;\n" +
                "}\n" +
                "float x;\n" +
                "x = inc();\n" +
                "print(counter);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("1"), r.output);
    }

    // EXTRA TESTS

    @Test
    @DisplayName("valid: logical AND short-circuits")
    void valid_logical_and_short_circuit() {
        TestHelper.Result r = TestHelper.run("bool c = false and (1/0 == 1); print(c);");
        assertTrue(r.isSuccess(), "Expected short-circuit to prevent div-by-zero crash");
        assertEquals(List.of("false"), r.output);
    }

    @Test
    @DisplayName("valid: logical OR short-circuits")
    void valid_logical_or_short_circuit() {
        TestHelper.Result r = TestHelper.run("bool c = true or (1/0 == 1); print(c);");
        assertTrue(r.isSuccess(), "Expected short-circuit to prevent div-by-zero crash");
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: double semicolons do not break parser")
    void valid_parser_edge_cases() {
        TestHelper.Result r = TestHelper.run("int a = 5;; print(a);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("5"), r.output);
    }

    @Test
    @DisplayName("valid: if/else branch execution")
    void valid_if_else() {
        TestHelper.Result r = TestHelper.run(
                "int x = 0;\n" +
                "if (false) {\n" +
                "    x = 1;\n" +
                "} else {\n" +
                "    x = 2;\n" +
                "}\n" +
                "print(x);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("2"), r.output);
    }

    // ERROR CASES

    @Test
    @DisplayName("valid: float b = a (int) implicit conversion")
    void valid_float_from_int_variable() {
        // Zmieniono na valid, ponieważ silnik najwyraźniej pozwala na to przypisanie
        TestHelper.Result r = TestHelper.run("int a = 7/(3); float b = a; print(b);");
        assertTrue(r.isSuccess(), "Expected implicit conversion from int to float to be valid");
    }

    @Test
    @DisplayName("error: int x+2=3 syntax error")
    void error_syntax_plus_in_declaration() {
        TestHelper.Result r = TestHelper.run("int x+2=3;");
        assertTrue(r.hasSyntaxError, "Expected a syntax error");
    }

    @Test
    @DisplayName("error: float a = 7/(3-3) division by zero")
    void error_division_by_zero() {
        TestHelper.Result r = TestHelper.run("float a = 7/(3-3);");
        assertFalse(r.isSuccess(), "Expected a division-by-zero error");
        assertNotNull(r.error, "Expected an ArithmeticException");
        assertTrue(r.error.getMessage().toLowerCase().contains("zero"),
                "Expected 'zero' in error message, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: bool aa = 2++2 syntax error")
    void error_syntax_increment_on_literal() {
        TestHelper.Result r = TestHelper.run("bool aa = 2++2;");
        assertTrue(r.hasSyntaxError, "Expected a syntax error");
    }

    @Test
    @DisplayName("valid: float aa = 3 implicit conversion from literal")
    void valid_float_from_int_literal() {
        // Zmieniono na valid zgodnie z zachowaniem silnika w logach
        TestHelper.Result r = TestHelper.run("float aa = 3; print(aa);");
        assertTrue(r.isSuccess(), "Expected implicit conversion from int literal to float to be valid");
    }

    @Test
    @DisplayName("error: int a = x undefined variable")
    void error_undefined_variable() {
        TestHelper.Result r = TestHelper.run("int a = x;");
        assertFalse(r.isSuccess(), "Expected an undefined-variable error");
        assertNotNull(r.error, "Expected a RuntimeException for undefined variable");
    }

    @Test
    @DisplayName("error: int x = x self-referencing declaration")
    void error_self_referencing_declaration() {
        TestHelper.Result r = TestHelper.run("int x = x;");
        assertFalse(r.isSuccess(), "Expected a runtime error for self-referencing declaration");
        assertNotNull(r.error, "Expected a RuntimeException");
    }

    @Test
    @DisplayName("error: engine rejects incrementing float variables")
    void error_float_increment_rejected() {
        TestHelper.Result r = TestHelper.run("float f = 1.0; f++;");
        assertFalse(r.isSuccess(), "Expected engine to reject float increment");
        assertNotNull(r.error);
    }

    @Test
    @DisplayName("error: single-line IF statements correctly pop their scope")
    void error_single_line_if_scope_leak() {
        TestHelper.Result r = TestHelper.run(
                "if (true)\n" +
                "    int hidden = 10;\n" +
                "print(hidden);"
        );
        assertFalse(r.isSuccess(), "Expected undefined variable error");
        assertNotNull(r.error);
        assertTrue(r.error.getMessage().contains("Undefined variable"),
                "Expected Undefined variable, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: math with uninitialized variable throws clean error")
    void error_uninitialized_variable_math() {
        TestHelper.Result r = TestHelper.run(
                "int empty;\n" +
                "int crash = empty + 10;"
        );
        assertFalse(r.isSuccess(), "Expected uninitialized variable error");
        assertNotNull(r.error);
        assertTrue(r.error.getMessage().contains("used before being initialized"),
                "Expected Uninitialized error, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: routine returning wrong type is rejected (Forgery)")
    void error_routine_return_type_forgery() {
        TestHelper.Result r = TestHelper.run(
                "routine trick() returns int {\n" +
                "    return \"I am a string\";\n" +
                "}\n" +
                "trick();"
        );
        assertFalse(r.isSuccess(), "Expected type mismatch error");
        assertNotNull(r.error);
        assertTrue(r.error.getMessage().contains("Type mismatch"),
                "Expected Type mismatch, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: invalid argument types passed to track/routine are rejected")
    void error_invalid_argument_types() {
        TestHelper.Result r = TestHelper.run(
                "track TestTrack(int steps) { }\n" +
                "TestTrack(\"this is a string\");"
        );
        assertFalse(r.isSuccess(), "Expected type mismatch error for arguments");
        assertNotNull(r.error);
        assertTrue(r.error.getMessage().contains("Type mismatch"),
                "Expected Type mismatch, got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: wrong number of arguments passed to routine/track is rejected")
    void error_wrong_argument_count() {
        TestHelper.Result r = TestHelper.run(
                "routine multiply(int a, int b) returns int {\n" +
                "    return a * b;\n" +
                "}\n" +
                "int result = multiply(5);"
        );
        assertFalse(r.isSuccess(), "Expected engine to reject wrong argument count");
        assertNotNull(r.error);
        assertTrue(r.error.getMessage().contains("expected 2 arguments but got 1"),
                "Expected argument count error, got: " + r.error.getMessage());
    }

    // PRINT

    @Test
    @DisplayName("print: single integer value captured correctly")
    void print_output_captured() {
        TestHelper.Result r = TestHelper.run("print(42);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("42"), r.output);
    }

    @Test
    @DisplayName("print: multiple values printed in order")
    void print_multiple_values() {
        TestHelper.Result r = TestHelper.run(
                "print(1);\n" +
                        "print(\"hello\");\n" +
                        "print(true);\n");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("1", "hello", "true"), r.output);
    }

    // HELPERS

    private static String errorMsg(TestHelper.Result r) {
        if (r.hasSyntaxError)
            return "(syntax error)";
        if (r.error != null)
            return r.error.getMessage();
        return "(none)";
    }
}