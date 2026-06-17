import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FinalTests {

    @Test
    @DisplayName("Example 1: static scoping and parent:: operator inside blocks")
    void example1_blocks_static_scoping() {
        String code =
            "int x = 1; " +
            "int y = 100; " +
            "{ " +
            "  int x = 2; " +
            "  { " +
            "    int x = 3; " +
            "    print(x); " +
            "    print(parent::x); " +
            "    print(parent::parent::x); " +
            "    print(y); " +
            "    print(parent::y); " +
            "    print(parent::parent::y); " +
            "  } " +
            "}";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
        assertEquals(List.of("3", "2", "1", "100", "100", "100"), r.output);
    }

    @Test
    @DisplayName("Example 2: recursion and static scoping with parent::")
    void example2_recursion_parent_scope() {
        String code =
            "int x = 100; " +
            "routine foo(int n) returns int { " +
            "  int x = n; " +
            "  if(n > 0) { " +
            "    foo(n-1); " +
            "  } " +
            "  print(x); " +
            "  print(parent::x); " +
            "  return 0; " +
            "} " +
            "foo(6);";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
        assertEquals(List.of(
            "0", "100",
            "1", "100",
            "2", "100",
            "3", "100",
            "4", "100",
            "5", "100",
            "6", "100"
        ), r.output);
    }

    @Test
    @DisplayName("Example 3: parameter shadowing global variable")
    void example3_parameter_shadowing() {
        String code =
            "int x = 100; " +
            "routine foo(int x) returns void { " +
            "  print(x); " +
            "  print(parent::x); " +
            "} " +
            "foo(5);";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
        assertEquals(List.of("5", "100"), r.output);
    }

    @Test
    @DisplayName("Example 4: nested loops infinite execution returns error message")
    void example4_nested_loops() {
        String code =
            "loop(int i from 0 to 3) { " +
            "  loop(int j from 0 to i) { " +
            "    i++; " +
            "  } " +
            "}";

        TestHelper.Result r = TestHelper.run(code);

        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Example 5: assignment checking without explicit cast")
    void example5_assignments_and_types() {
        String code =
            "int i = 1; " +
            "float f = 1.0; " +
            "int i2 = 1; " +
            "int i3 = 1; " +
            "int i4 = 1; " +
            "bool b = true;";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("Example 6: mixed division comparison in loop condition")
    void example6_mixed_division_condition() {
        String code =
            "loop(int i from 0 to 5) { " +
            "  if(i / 2.0 == i / 2) { " +
            "    print(i); " +
            "  } " +
            "}";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
        assertEquals(List.of("0", "2", "4"), r.output);
    }

    @Test
    @DisplayName("Example 7: mutual recursion of routines")
    void example7_mutual_recursion() {
        String code =
            "routine foo1(int a) returns int { " +
            "  print(a); " +
            "  if(a > 0) { " +
            "    foo2(a-1); " +
            "  } " +
            "  return 0; " +
            "} " +
            "routine foo2(int a) returns int { " +
            "  print(a); " +
            "  if(a > 0) { " +
            "    foo1(a-2); " +
            "  } " +
            "  return 1; " +
            "} " +
            "foo1(4);";

        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess());
        assertEquals(List.of("4", "3", "1", "0"), r.output);
    }
}