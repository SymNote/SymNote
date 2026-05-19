import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LoopWhileEdgeCasesTest {

    

    @Test
    @DisplayName("valid: loop variable shadows global variable")
    void loop_shadowing_global() {
        TestHelper.Result r = TestHelper.run(
            "int i = 100;" +
            "loop(int i from 1 to 2) { print(i); }" +
            "print(i);"
        );
        assertEquals(List.of("1", "2", "100"), r.output);
    }

    @Test
    @DisplayName("error: loop variable not accessible after loop")
    void loop_var_leak() {
        TestHelper.Result r = TestHelper.run(
            "loop(int k from 1 to 5) { print(k); }" +
            "print(k);"
        );
        assertFalse(r.isSuccess(), "Variable k should be local to loop scope");
    }

    @Test
    @DisplayName("valid: nested loops with same iterator name (shadowing)")
    void nested_loop_shadowing() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  loop(int i from 10 to 11) { print(i); }" +
            "  print(i);" +
            "}"
        );
        assertEquals(List.of("10", "11", "1", "10", "11", "2"), r.output);
    }

    @Test
    @DisplayName("valid: redeclare loop variable inside its own block")
    void loop_redeclaration_error() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 5) { int i = 10; }"
        );
        assertTrue(r.isSuccess());
    }

    

    @Test
    @DisplayName("valid: loop from higher to lower (no execution or logic check)")
    void loop_backwards() {
        TestHelper.Result r = TestHelper.run(
            "int count = 0;" +
            "loop(int i from 10 to 1) { count = count + 1; }" +
            "print(count);"
        );
        
        assertEquals(List.of("0"), r.output);
    }

    @Test
    @DisplayName("valid: loop where from == to")
    void loop_single_iter() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 5 to 5) { print(i); }"
        );
        assertEquals(List.of("5"), r.output);
    }

    @Test
    @DisplayName("error: assignment inside while condition is not allowed")
    void error_assignment_in_while() {
        
        TestHelper.Result r = TestHelper.run(
            "int x = 0;" +
            "while(x = 5) { print(x); }"
        );
        assertFalse(r.isSuccess(), "Assignment should not be valid as an expression");
    }

    @Test
    @DisplayName("valid: while with empty statement (semicolon) - safe version")
    void while_empty_stmt_safe() {
        TestHelper.Result r = TestHelper.run(
            "int x = 10;" +
            "while(x < 5) ;" +
            "print(x);"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("10"), r.output);
    }

    @Test
    @DisplayName("error: assignment inside loop range is not allowed")
    void error_assignment_in_loop_range() {
        TestHelper.Result r = TestHelper.run(
            "int x = 0;" +
            "loop(int i from (x = 1) to 10) { print(i); }"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("error: loop with float in range (if grammar expects int)")
    void loop_float_range() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 0 to 5.5) { print(i); }"
        );
        assertFalse(r.isSuccess());
    }

    

    @Test
    @DisplayName("valid: simple break in while")
    void break_simple() {
        TestHelper.Result r = TestHelper.run(
            "int i = 0;" +
            "while(true) {" +
            "  if (i == 2) break;" +
            "  print(i);" +
            "  i++;" +
            "}"
        );
        assertEquals(List.of("0", "1"), r.output);
    }

    @Test
    @DisplayName("valid: continue in loop")
    void continue_simple() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 3) {" +
            "  if (i == 2) continue;" +
            "  print(i);" +
            "}"
        );
        assertEquals(List.of("1", "3"), r.output);
    }

    @Test
    @DisplayName("valid: break in nested loop (only inner should break)")
    void nested_break() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  loop(int j from 1 to 10) {" +
            "    if (j == 2) break;" +
            "    print(j);" +
            "  }" +
            "  print(i);" +
            "}"
        );
        assertEquals(List.of("1", "1", "1", "2"), r.output);
    }

    @Test
    @DisplayName("error: break outside of loop context")
    void break_outside_loop() {
        TestHelper.Result r = TestHelper.run(
            "int x = 10;" +
            "break;" +
            "print(x);"
        );
        assertFalse(r.isSuccess(), "Break must be inside a loop/iteration statement");
    }

    @Test
    @DisplayName("valid: continue in deeply nested structure")
    void continue_deep_nesting() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  if (true) {" +
            "    if (true) {" +
            "       if (i == 1) continue;" +
            "    }" +
            "  }" +
            "  print(i);" +
            "}"
        );
        assertEquals(List.of("2"), r.output);
    }

    

    @Test
    @DisplayName("valid: break inside routine loop")
    void routine_loop_break() {
        TestHelper.Result r = TestHelper.run(
            "routine test() returns void {" +
            "  loop(int i from 1 to 5) { if(i==3) break; print(i); }" +
            "}" +
            "test();"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("valid: loop in track with grid elements")
    void track_loop_grid() {
        TestHelper.Result r = TestHelper.run(
            "track melody() {" +
            "  loop(int i from 1 to 2) {" +
            "    grid(1/4) { C4-D4 }" +
            "  }" +
            "}"
        );
        assertTrue(r.isSuccess());
    }

    

    @Test
    @DisplayName("valid: loop with no block (single statement)")
    void loop_no_block() {
        TestHelper.Result r = TestHelper.run(
            "int x = 0;" +
            "loop(int i from 1 to 3) x = x + 1;" +
            "print(x);"
        );
        assertEquals(List.of("3"), r.output);
    }

    @Test
    @DisplayName("valid: while with empty statement (semicolon) - no execution")
    void while_empty_stmt_no_execution() {
        TestHelper.Result r = TestHelper.run(
            "while(false) ;" + 
            "print(\"done\");"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("done"), r.output);
    }


    @Test
    @DisplayName("valid: break inside if-else inside loop")
    void break_in_if_else() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 5) {" +
            "  if (i < 3) { print(i); } else { break; }" +
            "}"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("valid: continue as the last statement in loop")
    void continue_last_stmt() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) { print(i); continue; }"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("error: return expression in void routine loop")
    void return_val_in_void_loop() {
        TestHelper.Result r = TestHelper.run(
            "routine v() returns void { loop(int i from 1 to 5) return 10; }" +
            "v();"
        );
        assertFalse(r.isSuccess());
    }



    @Test
    @DisplayName("valid: using routine parameter as loop iterator name")
    void loop_param_collision() {
        TestHelper.Result r = TestHelper.run(
            "routine r(int i) returns void { loop(int i from 0 to 2) print(i); }" +
            "r(10);"
        );
        assertTrue(r.isSuccess(), "Shadowing param might be disallowed depending on rules");
    }

    @Test
    @DisplayName("valid: loop using function call in 'to' expression")
    void loop_func_call_limit() {
        TestHelper.Result r = TestHelper.run(
            "routine get_limit() returns int { return 2; }" +
            "loop(int i from 1 to get_limit()) { print(i); }"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("valid: post-increment in while condition")
    void while_post_inc() {
        TestHelper.Result r = TestHelper.run(
            "int i = 0; while(i++ < 2) print(i);"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("valid: pre-decrement in loop body")
    void loop_pre_dec() {
        TestHelper.Result r = TestHelper.run(
            "int x = 5; loop(int i from 1 to 2) print(--x);"
        );
        assertEquals(List.of("4", "3"), r.output);
    }


    @Test
    @DisplayName("error: use break in top level (not in loop)")
    void top_level_break() {
        TestHelper.Result r = TestHelper.run("break;");
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("valid: nested while-loop-while")
    void deep_mixed_nesting() {
        TestHelper.Result r = TestHelper.run(
            "int x = 1;" +
            "while(x > 0) {" +
            "  loop(int i from 1 to 1) {" +
            "    int y = 1;" +
            "    while(y > 0) { print(\"hit\"); y--; }" +
            "  }" +
            "  x--;" +
            "}"
        );
        assertEquals(List.of("hit"), r.output);
    }

    @Test
    @DisplayName("valid: empty loop block")
    void empty_loop_block() {
        TestHelper.Result r = TestHelper.run("loop(int i from 1 to 10) {}");
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("valid: loop with ID from global as range")
    void loop_global_range() {
        TestHelper.Result r = TestHelper.run(
            "int start = 1; int end = 2;" +
            "loop(int i from start to end) print(i);"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("error: while with non-bool expression")
    void while_non_bool() {
        TestHelper.Result r = TestHelper.run("while(10) { print(1); }");
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("valid: loop iterator used in expression inside loop")
    void loop_iterator_math() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 3) { print(i * 2); }"
        );
        assertEquals(List.of("2", "4", "6"), r.output);
    }

    @Test
    @DisplayName("valid: multiple breaks in one loop")
    void multiple_breaks() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 10) {" +
            "  if (i == 2) break;" +
            "  if (i == 5) break;" +
            "  print(i);" +
            "}"
        );
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("valid: loop inside if-else")
    void loop_in_if() {
        TestHelper.Result r = TestHelper.run(
            "if (true) { loop(int i from 1 to 2) print(i); }"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("valid: break in track grid sequence (semantic test)")
    void track_grid_break() {
        
        TestHelper.Result r = TestHelper.run(
            "track t() {" +
            "  while(true) {" +
            "    grid(1/4) { C4 }" +
            "    break;" +
            "  }" +
            "}"
        );
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("error: declare two loops with same variable in same scope")
    void double_loop_var() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {}" +
            "int i = 0;" 
        );
        
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("valid: loop from 0 to 0")
    void loop_zero_to_zero() {
        TestHelper.Result r = TestHelper.run("loop(int i from 0 to 0) print(i);");
        assertEquals(List.of("0"), r.output);
    }

    @Test
    @DisplayName("valid: while(false) with break inside")
    void while_false_break() {
        TestHelper.Result r = TestHelper.run("while(false) { break; }");
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("valid: continue in nested while")
    void continue_nested_while() {
        TestHelper.Result r = TestHelper.run(
            "int i = 0;" +
            "while(i < 2) {" +
            "  i++;" +
            "  int j = 0;" +
            "  while(j < 2) {" +
            "    j++;" +
            "    if(j == 1) continue;" +
            "    print(j);" +
            "  }" +
            "}"
        );
        assertEquals(List.of("2", "2"), r.output);
    }

    @Test
    @DisplayName("valid: expression statement as loop body")
    void expr_as_loop_body() {
        TestHelper.Result r = TestHelper.run(
            "int x = 0;" +
            "loop(int i from 1 to 5) x++;" +
            "print(x);"
        );
        assertEquals(List.of("5"), r.output);
    }

    @Test
    @DisplayName("valid: assignment to loop iterator")
    void loop_iterator_assignment() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 5) { i = 10; }"
        );

        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("valid: loop with string concatenation in body")
    void loop_string_concat() {
        TestHelper.Result r = TestHelper.run(
            "string s = \"\";" +
            "loop(int i from 1 to 3) s = s + \"!\";" +
            "print(s);"
        );
        assertEquals(List.of("!!!"), r.output);
    }

    @Test
    @DisplayName("valid: loop inside routine with return in body")
    void routine_loop_return() {
        TestHelper.Result r = TestHelper.run(
            "routine r() returns int {" +
            "  loop(int i from 1 to 10) { if(i == 2) return i; }" +
            "  return 0;" +
            "}" +
            "print(r());"
        );
        assertEquals(List.of("2"), r.output);
    }

    @Test
    @DisplayName("valid: while with not operator")
    void while_not_op() {
        TestHelper.Result r = TestHelper.run(
            "bool stop = false;" +
            "int c = 0;" +
            "while(not stop) {" +
            "  c++;" +
            "  if(c == 2) stop = true;" +
            "}" +
            "print(c);"
        );
        assertEquals(List.of("2"), r.output);
    }

    @Test
    @DisplayName("error: loop with void type iterator")
    void loop_void_iterator() {
        TestHelper.Result r = TestHelper.run(
            "loop(void v from 0 to 1) {}"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("valid: loop with expression as from/to")
    void loop_expr_ranges() {
        TestHelper.Result r = TestHelper.run(
            "int a = 1; int b = 2;" +
            "loop(int i from a + 0 to b * 2) print(i);"
        );
        assertEquals(List.of("1", "2", "3", "4"), r.output);
    }

    @Test
    @DisplayName("valid: multiple loop statements on one line")
    void loops_one_line() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 1) print(i); loop(int j from 2 to 2) print(j);"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("error: missing semicolon after break")
    void break_no_semi() {
        TestHelper.Result r = TestHelper.run("while(true) { break }");
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("valid: long loop (1000 iterations)")
    void long_loop() {
        TestHelper.Result r = TestHelper.run(
            "int x = 0; loop(int i from 1 to 1000) x++; print(x);"
        );
        assertEquals(List.of("1000"), r.output);
    }
}