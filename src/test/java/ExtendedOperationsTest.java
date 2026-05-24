import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ExtendedOperationsTest {

    @Test
    @DisplayName("advanced: recursive factorial calculation")
    void test_recursion_factorial() {
        String code =
            "routine factorial(int n) returns int {\n" +
            "    if (n == 0) {\n" +
            "        return 1;\n" +
            "    }\n" +
            "    return n * factorial(n - 1);\n" +
            "}\n" +
            "print(factorial(5));";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Błąd podczas wykonywania rekurencji: " + getErrorMessage(r));
        assertEquals(List.of("120"), r.output);
    }

    @Test
    @DisplayName("advanced: variable shadowing in nested scopes")
    void test_variable_shadowing() {
        String code =
            "int x = 10;\n" +
            "routine check_shadow() returns void {\n" +
            "    int x = 5;\n" +
            "    print(x);\n" +
            "}\n" +
            "check_shadow();\n" +
            "print(x);";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Błąd: " + getErrorMessage(r));
        
        assertEquals(List.of("5", "10"), r.output);
    }

    @Test
    @DisplayName("advanced: nested block scopes with same variable name")
    void test_nested_blocks() {
        String code =
            "int a = 1;\n" +
            "{\n" +
            "    int a = 2;\n" +
            "    {\n" +
            "        int a = 3;\n" +
            "        print(a);\n" +
            "    }\n" +
            "    print(a);\n" +
            "}\n" +
            "print(a);";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Błąd zasięgu blokowego: " + getErrorMessage(r));
        assertEquals(List.of("3", "2", "1"), r.output);
    }

    @Test
    @DisplayName("advanced: complex math precedence")
    void test_math_precedence() {
        
        String code = "int res = 10 + 2 * 5 - 20 / 4; print(res);";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
        assertEquals(List.of("15"), r.output);
    }

    @Test
    @DisplayName("advanced: scope leak prevention in while loops")
    void test_while_scope_leak() {
        String code =
            "int i = 0;\n" +
            "while (i < 1) {\n" +
            "    int temp = 42;\n" +
            "    i++;\n" +
            "}\n" +
            "print(temp);"; 

        TestHelper.Result r = TestHelper.run(code);
        assertFalse(r.isSuccess(), "Zmienna 'temp' wyciekła poza pętlę while!");
        assertTrue(r.error.getMessage().contains("temp"),
            "Oczekiwano błędu dotyczącego 'temp', dostano: " + r.error.getMessage());
    }

    @Test
    @DisplayName("advanced: fibonacci sequence (recursion)")
    void test_fibonacci_recursion() {
        String code =
            "routine fib(int n) returns int {\n" +
            "    if (n <= 1) { return n; }\n" +
            "    return fib(n - 1) + fib(n - 2);\n" +
            "}\n" +
            "print(fib(6));"; 

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
        assertEquals(List.of("8"), r.output);
    }

    @Test
    @DisplayName("error: re-declaring variable in the same scope")
    void error_duplicate_declaration() {
        String code =
            "int x = 5;\n" +
            "int x = 10;";

        TestHelper.Result r = TestHelper.run(code);
        assertFalse(r.isSuccess(), "Interpreter powinien zabronić redeklaracji 'x' w tym samym scope");
    }

    private String getErrorMessage(TestHelper.Result r) {
        if (r.hasSyntaxError) return "Syntax Error";
        return (r.error != null) ? r.error.getMessage() : "Unknown error";
    }
}