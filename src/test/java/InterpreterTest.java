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

    // ERROR CASES

    @Test
    @DisplayName("error: float b = a (int) type mismatch")
    void error_float_from_int_variable() {
        TestHelper.Result r = TestHelper.run("int a = 7/(3); float b = a;");
        assertFalse(r.isSuccess(), "Expected a type mismatch error");
        assertNotNull(r.error, "Expected a RuntimeException for type mismatch");
        assertTrue(r.error.getMessage().contains("Type mismatch"),
                "Expected 'Type mismatch' in error message, got: " + r.error.getMessage());
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
    @DisplayName("error: float aa = 3 type mismatch")
    void error_float_from_int_literal() {
        TestHelper.Result r = TestHelper.run("float aa = 3;");
        assertFalse(r.isSuccess(), "Expected a type mismatch error");
        assertNotNull(r.error, "Expected a RuntimeException for type mismatch");
        assertTrue(r.error.getMessage().contains("Type mismatch"),
                "Expected 'Type mismatch' in error message, got: " + r.error.getMessage());
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
