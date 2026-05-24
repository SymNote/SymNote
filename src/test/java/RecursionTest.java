import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecursionTest {

    private String errorMsg(TestHelper.Result r) {
        if (r.error != null) {
            return r.error.getMessage();
        }
        if (r.hasSyntaxError) {
            return "Syntax error occurred";
        }
        return "No error";
    }

    @Test
    @DisplayName("valid: standard factorial recursion (n=5)")
    void valid_factorial_recursion() {
        String code = 
            "routine factorial(int n) returns int {\n" +
            "    if (n == 0) return 1;\n" +
            "    return n * factorial(n - 1);\n" +
            "}\n" +
            "int result = factorial(5);\n" +
            "print(result);";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("120"), r.output);
    }

    @Test
    @DisplayName("error: deep recursion should trigger custom SymNote StackOverflow")
    void error_custom_stackoverflow() {
        String code = 
            "routine infinite(int n) returns int {\n" +
            "    return infinite(n + 1);\n" +
            "}\n" +
            "infinite(1);";
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertFalse(r.isSuccess(), "Expected an error but script finished successfully");
        assertNotNull(r.error, "Expected an exception to be thrown");
        
        boolean hasCustomError = r.error.getMessage() != null && 
                                 r.error.getMessage().contains("SymNote StackOverflow");
                                 
        assertTrue(hasCustomError, "Expected custom 'SymNote StackOverflow' error, but got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("valid: deep recursion just under the maximum stack limit (150)")
    void valid_deep_recursion_under_limit() {
        String code = 
            "routine climb(int n) returns int {\n" +
            "    if (n == 0) return 0;\n" +
            "    return 1 + climb(n - 1);\n" +
            "}\n" +
            "print(climb(150));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error for deep recursion under limit, but got: " + errorMsg(r));
        assertEquals(List.of("150"), r.output);
    }

    @Test
    @DisplayName("valid: mutual recursion (A calls B, B calls A)")
    void valid_mutual_recursion() {
        String code = 
            "routine is_even(int n) returns bool {\n" +
            "    if (n == 0) return true;\n" +
            "    return is_odd(n - 1);\n" +
            "}\n" +
            "routine is_odd(int n) returns bool {\n" +
            "    if (n == 0) return false;\n" +
            "    return is_even(n - 1);\n" +
            "}\n" +
            "print(is_even(10));\n" +
            "print(is_odd(11));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error in mutual recursion, but got: " + errorMsg(r));
        assertEquals(List.of("true", "true"), r.output);
    }

    @Test
    @DisplayName("valid: recursion with local variable shadowing and scope integrity")
    void valid_recursion_scope_integrity() {
        String code = 
            "routine sum_down(int n) returns int {\n" +
            "    int local_val = n;\n" +
            "    if (n == 0) return 0;\n" +
            "    int next_val = sum_down(n - 1);\n" +
            "    return local_val + next_val;\n" +
            "}\n" +
            "print(sum_down(5));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error in scope integrity test, but got: " + errorMsg(r));
        assertEquals(List.of("15"), r.output);
    }
}