import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class NestedLoopTest {

    @Test
    @DisplayName("Valid: Simple 2D grid iteration")
    void validNestedSimpleGrid() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  loop(int j from 1 to 2) { print(i + \",\" + j); }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1,1", "1,2", "2,1", "2,2"), r.output);
    }

    @Test
    @DisplayName("Valid: Shadowing iterator name in nested loop")
    void validNestedShadowingIterator() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  loop(int i from 10 to 11) { print(i); }" +
            "  print(i);" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("10", "11", "1", "10", "11", "2"), r.output);
    }

    @Test
    @DisplayName("Valid: Inner loop boundary depends on outer iterator")
    void validNestedBoundaryDependent() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 3) {" +
            "  loop(int j from 1 to i) { print(j); }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1", "1", "2", "1", "2", "3"), r.output);
    }

    @Test
    @DisplayName("Valid: Changing outer iterator inside inner loop")
    void validMutateOuterIterator() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 5) {" +
            "  loop(int j from 1 to 2) { i = 10; }" +
            "}"
        );
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("Valid: Changing loop 'to' variable inside while")
    void validWhileBoundaryMutation() {
        TestHelper.Result r = TestHelper.run(
            "int limit = 2;" +
            "int x = 0;" +
            "while(x < limit) {" +
            "  print(x);" +
            "  limit = 10;" +
            "  x = x + 4;" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("0", "4", "8"), r.output);
    }

    @Test
    @DisplayName("Valid: Loop 'to' boundary changed")
    void validLoopBoundarySnapshot() {
        TestHelper.Result r = TestHelper.run(
            "int end = 2;" +
            "loop(int i from 1 to end) {" +
            "  print(i);" +
            "  end = 7;" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1", "2", "3", "4", "5", "6", "7"), r.output);
    }

    @Test
    @DisplayName("Valid: Triple nested loops with sum")
    void validTripleNestedSum() {
        TestHelper.Result r = TestHelper.run(
            "int total = 0;" +
            "loop(int i from 1 to 2) {" +
            "  loop(int j from 1 to 2) {" +
            "    loop(int k from 1 to 2) { total = total + 1; }" +
            "  }" +
            "}" +
            "print(total);"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("8"), r.output);
    }

    @Test
    @DisplayName("Valid: Break from inner loop")
    void validNestedBreakInner() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  loop(int j from 1 to 10) {" +
            "    if(j == 2) break;" +
            "    print(i + \":\" + j);" +
            "  }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1:1", "2:1"), r.output);
    }

    @Test
    @DisplayName("Valid: Continue in outer loop")
    void validNestedContinueOuter() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 3) {" +
            "  if(i == 2) continue;" +
            "  loop(int j from 1 to 1) { print(i); }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1", "3"), r.output);
    }

    @Test
    @DisplayName("Valid: While condition modified by routine call side effect")
    void validWhileRoutineSideEffect() {
        TestHelper.Result r = TestHelper.run(
            "int g = 0;" +
            "routine check() returns bool { g = g + 1; return g < 4; }" +
            "while(check()) { print(g); }"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1", "2", "3"), r.output);
    }

    @Test
    @DisplayName("Valid: Scoping in if inside loop")
    void validScopingIfInLoop() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  if(true) { int x = i; print(x); }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1", "2"), r.output);
    }

@Test
@DisplayName("Valid: Dynamic boundary modification")
void validDynamicBoundary() {
    TestHelper.Result r = TestHelper.run(
        "int end = 2;" +
        "int count = 0;" +
        "loop(int i from 1 to end) {" +
        "  print(i);" +
        "  if(i == 1) { end = 5; }" +
        "  count++;" +
        "  if(count > 10) break;" +
        "}"
    );
    assertTrue(r.isSuccess());
    assertEquals(List.of("1", "2", "3", "4", "5"), r.output);
}

    @Test
    @DisplayName("Valid: While inside Loop with break")
    void validWhileInsideLoopBreak() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  int j = 0;" +
            "  while(true) {" +
            "    j++;" +
            "    if(j == 2) break;" +
            "    print(i + \"-\" + j);" +
            "  }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1-1", "2-1"), r.output);
    }

    @Test
    @DisplayName("Valid: Loop inside While with continue")
    void validLoopInsideWhileContinue() {
        TestHelper.Result r = TestHelper.run(
            "int x = 0;" +
            "while(x < 2) {" +
            "  x++;" +
            "  loop(int i from 1 to 2) { if(i == 2) continue; print(x + \":\" + i); }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1:1", "2:1"), r.output);
    }

    @Test
    @DisplayName("Valid: 5 levels of nesting")
    void validDeepNesting() {
        TestHelper.Result r = TestHelper.run(
            "loop(int a from 1 to 1) { loop(int b from 1 to 1) { loop(int c from 1 to 1) {" +
            "loop(int d from 1 to 1) { loop(int e from 1 to 1) { print(\"deep\"); } } } } }"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("deep"), r.output);
    }

    @Test
    @DisplayName("Valid: Iterator used as boundary for next loop")
    void validIteratorAsNextBoundary() {
        TestHelper.Result r = TestHelper.run(
            "int last = 0;" +
            "loop(int i from 1 to 3) {" +
            "  loop(int j from last to i) { print(j); }" +
            "  last = i;" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("0", "1", "1", "2", "2", "3"), r.output);
    }

    @Test
    @DisplayName("Error: Shadowing with different types")
    void errorNestedShadowingTypes() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 1) {" +
            "  loop(string i from \"a\" to \"a\") { print(i); }" +
            "}"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Valid: Loop 'from' boundary expression side effect")
    void validLoopFromSideEffect() {
        TestHelper.Result r = TestHelper.run(
            "int start = 5;" +
            "loop(int i from start-- to 5) { print(i); }" +
            "print(start);"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("5", "4"), r.output);
    }

    @Test
    @DisplayName("Valid: Return from routine inside nested loops")
    void validRoutineReturnNested() {
        TestHelper.Result r = TestHelper.run(
            "routine test() returns int {" +
            "  loop(int i from 1 to 10) {" +
            "    loop(int j from 1 to 10) { if(i + j == 5) return i; }" +
            "  }" +
            "  return 0;" +
            "}" +
            "print(test());"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("Valid: While condition depends on nested loop result")
    void validWhileConditionNestedResult() {
        TestHelper.Result r = TestHelper.run(
            "int x = 0;" +
            "int sum = 0;" +
            "while(sum < 5) {" +
            "  loop(int i from 1 to 2) { sum = sum + i; }" +
            "  x++;" +
            "}" +
            "print(x);"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("2"), r.output);
    }

    @Test
    @DisplayName("Valid: Empty loops nesting")
    void validEmptyNestedLoops() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 100) { loop(int j from 1 to 100) { ; } }" +
            "print(\"done\");"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("done"), r.output);
    }

    @Test
    @DisplayName("Error: Decrementing loop iterator inside")
    void errorDecrementIterator() {
        TestHelper.Result r = TestHelper.run(
            "int safety = 0;" +
            "loop(int i from 1 to 5) {" +
            "  safety++;" +
            "  if (safety > 10) break;" +
            "  i--;" +
            "}"
        );

        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("Valid: Iterator name same as routine call")
    void validNestedIteratorNameCollision() {
        TestHelper.Result r = TestHelper.run(
            "routine x() returns int { return 10; }" +
            "loop(int x from 1 to 1) { print(x); }"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("Valid: Global variable boundary modified in nested routine (snapshot check)")
    void validBoundaryGlobalMutationNested() {
        TestHelper.Result r = TestHelper.run(
            "int limit = 2;" +
            "routine change() returns void { limit = 6; }" +
            "loop(int i from 1 to limit) {" +
            "  print(i);" +
            "  change();" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1", "2", "3", "4", "5", "6"), r.output);
    }

    @Test
    @DisplayName("Valid: Complex math in nested loop boundaries")
    void validNestedComplexMathBoundaries() {
        TestHelper.Result r = TestHelper.run(
            "int x = 1;" +
            "loop(int i from x to x * 2) {" +
            "  loop(int j from i - 1 to i + 1) { print(j); }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("0", "1", "2", "1", "2", "3"), r.output);
    }

    @Test
    @DisplayName("Valid: Changing boolean flag used in nested while")
    void validNestedWhileFlagMutation() {
        TestHelper.Result r = TestHelper.run(
            "bool run = true;" +
            "loop(int i from 1 to 2) {" +
            "  while(run) {" +
            "    print(i);" +
            "    run = false;" +
            "  }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("Valid: Nested loops with return in routine branches")
    void validNestedReturnBranches() {
        TestHelper.Result r = TestHelper.run(
            "routine find() returns string {" +
            "  loop(int i from 1 to 2) {" +
            "    loop(int j from 1 to 2) {" +
            "      if(i == 2) return \"found\";" +
            "    }" +
            "  }" +
            "  return \"not\";" +
            "}" +
            "print(find());"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("found"), r.output);
    }

    @Test
    @DisplayName("Valid: Nested loop boundary is a post-incremented ID")
    void validNestedPostIncBoundary() {
        TestHelper.Result r = TestHelper.run(
            "int x = 1;" +
            "int safety = 0;" +
            "loop(int i from 1 to 2) {" +
            "  loop(int j from 1 to x++) {" +
            "    safety++;" +
            "    if (safety > 5) break;" +
            "    print(x);" +
            "  }" +
            "  if (safety > 10) break;" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("2", "3", "4", "5", "6"), r.output);
    }

    @Test
    @DisplayName("Valid: Loop with identical from and to")
    void validLoopSameBoundaries() {
        TestHelper.Result r = TestHelper.run(
            "int x = 5;" +
            "loop(int i from x to x) { print(i); }"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("5"), r.output);
    }

    @Test
    @DisplayName("Valid: Matrix-like output with arithmetic")
    void validNestedArithmetic() {
        TestHelper.Result r = TestHelper.run(
            "loop(int i from 1 to 2) {" +
            "  loop(int j from 1 to 2) { print(i * j); }" +
            "}"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("1", "2", "2", "4"), r.output);
    }
}