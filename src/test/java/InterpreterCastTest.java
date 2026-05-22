import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterCastTest {
    private static String errorMsg(TestHelper.Result r) {
        if (r.hasSyntaxError)
            return "(syntax error)";
        if (r.error != null)
            return r.error.getMessage();
        return "(none)";
    }

    @Test
    @DisplayName("valid: cast int to float literal")
    void valid_cast_int_to_float_literal() {
        TestHelper.Result r = TestHelper.run("float f = (float) 5;; print(f);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("5.0"), r.output);
    }

    @Test
    @DisplayName("valid: cast int variable to float")
    void valid_cast_int_var_to_float() {
        TestHelper.Result r = TestHelper.run("int a = 10; float f = (float) a;; print(f);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("10.0"), r.output);
    }

    @Test
    @DisplayName("valid: cast float to int downcast truncates")
    void valid_cast_float_to_int_truncates() {
        TestHelper.Result r = TestHelper.run("int i = (int) 4.7;; print(i);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("4"), r.output);
    }

    @Test
    @DisplayName("valid: cast negative float to int")
    void valid_cast_negative_float_to_int() {
        TestHelper.Result r = TestHelper.run("float f = -3.9; int i = (int) f;; print(i);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("-3"), r.output);
    }

    @Test
    @DisplayName("valid: cast int to string")
    void valid_cast_int_to_string() {
        TestHelper.Result r = TestHelper.run("string s = (string) 123;; print(s);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("123"), r.output);
    }

    @Test
    @DisplayName("valid: cast float to string")
    void valid_cast_float_to_string() {
        TestHelper.Result r = TestHelper.run("string s = (string) 45.67;; print(s);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("45.67"), r.output);
    }

    @Test
    @DisplayName("valid: cast bool true to string")
    void valid_cast_bool_true_to_string() {
        TestHelper.Result r = TestHelper.run("string s = (string) true;; print(s);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: cast bool false to string")
    void valid_cast_bool_false_to_string() {
        TestHelper.Result r = TestHelper.run("string s = (string) false;; print(s);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("false"), r.output);
    }

    @Test
    @DisplayName("error: cast string representing int to int")
    void error_cast_string_to_int() {
        TestHelper.Result r = TestHelper.run("int i = (int) \"42\";; print(i);");
        assertFalse(r.isSuccess(), "Expected error but got: " + errorMsg(r));
    }

    @Test
    @DisplayName("error: cast string representing float to float")
    void error_cast_string_to_float() {
        TestHelper.Result r = TestHelper.run("float f = (float) \"3.14\";; print(f);");
        assertFalse(r.isSuccess(), "Expected error but got: " + errorMsg(r));
    }

    @Test
    @DisplayName("valid: cast int to bool zero is false")
    void valid_cast_int_zero_to_bool() {
        TestHelper.Result r = TestHelper.run("bool b = (bool) 0;; print(b);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("false"), r.output);
    }

    @Test
    @DisplayName("valid: cast int non-zero to bool is true")
    void valid_cast_int_nonzero_to_bool() {
        TestHelper.Result r = TestHelper.run("bool b = (bool) 5;; print(b);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: cast float zero to bool is false")
    void valid_cast_float_zero_to_bool() {
        TestHelper.Result r = TestHelper.run("bool b = (bool) 0.0;; print(b);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("false"), r.output);
    }

    @Test
    @DisplayName("valid: cast float non-zero to bool is true")
    void valid_cast_float_nonzero_to_bool() {
        TestHelper.Result r = TestHelper.run("bool b = (bool) -0.1;; print(b);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: cast bool to int")
    void valid_cast_bool_to_int() {
        TestHelper.Result r = TestHelper.run("int t = (int) true;; int f = (int) false;; print(t); print(f);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("1", "0"), r.output);
    }

    @Test
    @DisplayName("valid: cast bool to float")
    void valid_cast_bool_to_float() {
        TestHelper.Result r = TestHelper.run("float t = (float) true;; float f = (float) false;; print(t); print(f);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("1.0", "0.0"), r.output);
    }

    @Test
    @DisplayName("valid: identity casting int to int")
    void valid_identity_cast_int() {
        TestHelper.Result r = TestHelper.run("int i = (int) 10;; print(i);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("10"), r.output);
    }

    @Test
    @DisplayName("valid: identity casting float to float")
    void valid_identity_cast_float() {
        TestHelper.Result r = TestHelper.run("float f = (float) 5.5;; print(f);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("5.5"), r.output);
    }

    @Test
    @DisplayName("valid: cast expression inside mathematical context")
    void valid_cast_in_math_expression() {
        TestHelper.Result r = TestHelper.run("float res = 2.5 + (float) 3;; print(res);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("5.5"), r.output);
    }

    @Test
    @DisplayName("valid: cast complex expression results")
    void valid_cast_complex_expression() {
        TestHelper.Result r = TestHelper.run("int i = (int) (5.5 * 2.0);; print(i);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("11"), r.output);
    }

    @Test
    @DisplayName("valid: nested casts")
    void valid_nested_casts() {
        TestHelper.Result r = TestHelper.run("int i = (int) (float) (int) 3.14;; print(i);");
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("3"), r.output);
    }

    @Test
    @DisplayName("valid: cast routine invocation result")
    void valid_cast_routine_call_result() {
        TestHelper.Result r = TestHelper.run(
                "routine get_val() returns float { return 4.9; }\n" +
                "int i = (int) get_val();; print(i);"
        );
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("4"), r.output);
    }

    @Test
    @DisplayName("error: cast invalid string to int throws runtime exception")
    void error_cast_invalid_string_to_int() {
        TestHelper.Result r = TestHelper.run("int i = (int) \"not_an_int\";;");
        assertFalse(r.isSuccess(), "Expected parsing/conversion error for invalid string to int");
        assertNotNull(r.error);
    }

    @Test
    @DisplayName("error: cast invalid string to float throws runtime exception")
    void error_cast_invalid_string_to_float() {
        TestHelper.Result r = TestHelper.run("float f = (float) \"abc\";;");
        assertFalse(r.isSuccess(), "Expected conversion error for invalid string to float");
        assertNotNull(r.error);
    }


    @Test
    @DisplayName("error: cast string to note is forbidden")
    void error_cast_string_to_note() {
        TestHelper.Result r = TestHelper.run("note n = (note) \"C#5\";;");
        assertFalse(r.isSuccess(), "Casting from string to note must be forbidden");
        assertNotNull(r.error);
    }

    @Test
    @DisplayName("error: cast invalid string to note format")
    void error_cast_invalid_string_to_note() {
        TestHelper.Result r = TestHelper.run("note n = (note) \"H#9\";;");
        assertFalse(r.isSuccess(), "Expected conversion error for invalid note token representation");
        assertNotNull(r.error);
    }

    @Test
    @DisplayName("error: cast int to note is semantically invalid")
    void error_cast_int_to_note() {
        TestHelper.Result r = TestHelper.run("note n = (note) 65;;");
        assertFalse(r.isSuccess(), "Expected type mismatch or cast exception from int to note");
        assertNotNull(r.error);
    }

    @Test
    @DisplayName("error: cast float to note is semantically invalid")
    void error_cast_float_to_note() {
        TestHelper.Result r = TestHelper.run("note n = (note) 440.0;;");
        assertFalse(r.isSuccess(), "Expected type error casting float to musical note");
        assertNotNull(r.error);
    }


    @Test
    @DisplayName("error: type mismatch when assigning correctly casted type to wrong variable type")
    void error_cast_type_mismatch_assignment() {
        TestHelper.Result r = TestHelper.run("int i = (float) 10;;");
        assertFalse(r.isSuccess(), "Expected type mismatch when assigning float cast result to int variable");
        assertTrue(r.error.getMessage().contains("Type mismatch"));
    }

    @Test
    @DisplayName("error: cast void routine output throws runtime error")
    void error_cast_void_routine_call() {
        TestHelper.Result r = TestHelper.run(
                "routine do_nothing() returns void {}\n" +
                "string s = (string) do_nothing();|"
        );
        assertFalse(r.isSuccess(), "Expected error attempting to cast a void structure");
    }

    @Test
    @DisplayName("valid: implicit conversion int to float in assignment target")
    void valid_implicit_int_to_float_assign() {
        TestHelper.Result r = TestHelper.run("float f; f = 4; print(f);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("4.0"), r.output);
    }

    @Test
    @DisplayName("error: no implicit conversion float to int on reassignment")
    void error_no_implicit_float_to_int_reassign() {
        TestHelper.Result r = TestHelper.run("int i = 2; i = 3.5;");
        assertFalse(r.isSuccess(), "Expected type mismatch on implicit narrowing conversion");
        assertTrue(r.error.getMessage().contains("Type mismatch"));
    }

    @Test
    @DisplayName("valid: cast loop control variable inside iteration expression")
    void valid_cast_loop_variable() {
        TestHelper.Result r = TestHelper.run(
                "loop (int i from 1 to 2) {\n" +
                "    float f = (float) i;;\n" +
                "    print(f);\n" +
                "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1.0", "2.0"), r.output);
    }

    @Test
    @DisplayName("valid: cast unary minus expression result")
    void valid_cast_unary_expression() {
        TestHelper.Result r = TestHelper.run("int x = 5; float f = (float) -x;; print(f);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("-5.0"), r.output);
    }


    @Test
    @DisplayName("error: cast synth reference to int is illegal")
    void error_cast_synth_to_int() {
        TestHelper.Result r = TestHelper.run("synth my_synth; int i = (int) my_synth;;");
        assertFalse(r.isSuccess());
        assertNotNull(r.error);
    }

    @Test
    @DisplayName("error: cast sample token to string reference directly")
    void error_cast_sample_to_string() {
        TestHelper.Result r = TestHelper.run("sample smp; string s = (string) smp;;");
        assertFalse(r.isSuccess());
        assertNotNull(r.error);
    }
}