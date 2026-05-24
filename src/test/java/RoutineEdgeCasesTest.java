import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RoutineEdgeCasesTest {

    @Test
    @DisplayName("Routine: Simple void return execution")
    void routineVoidReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine hello() returns void { print(\"hi\"); }" +
            "hello();"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("hi"), r.output);
    }

    @Test
    @DisplayName("Routine: Return int value and print")
    void routineReturnInt() {
        TestHelper.Result r = TestHelper.run(
            "routine getFive() returns int { return 5; }" +
            "print(getFive());"
        );
        assertEquals(List.of("5"), r.output);
    }

    @Test
    @DisplayName("Routine: Parameters addition")
    void routineParams() {
        TestHelper.Result r = TestHelper.run(
            "routine add(int a, int b) returns int { return a + b; }" +
            "print(add(10, 20));"
        );
        assertEquals(List.of("30"), r.output);
    }

    @Test
    @DisplayName("Routine: String concatenation with parameter")
    void routineStringConcat() {
        TestHelper.Result r = TestHelper.run(
            "routine greet(string name) returns string { return \"Hello \" + name; }" +
            "print(greet(\"SymNote\"));"
        );
        assertEquals(List.of("Hello SymNote"), r.output);
    }

    @Test
    @DisplayName("Routine: Local variable scope isolation")
    void routineScopingLeak() {
        TestHelper.Result r = TestHelper.run(
            "routine set() returns void { int x = 100; }" +
            "set(); print(x);"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Factorial via recursion")
    void routineRecursionFactorial() {
        TestHelper.Result r = TestHelper.run(
            "routine fact(int n) returns int {" +
            "  if(n <= 1) return 1;" +
            "  return n * fact(n - 1);" +
            "}" +
            "print(fact(5));"
        );
        assertEquals(List.of("120"), r.output);
    }

    @Test
    @DisplayName("Routine: Parameter shadowing global variable")
    void routineShadowing() {
        TestHelper.Result r = TestHelper.run(
            "int x = 10;" +
            "routine test(int x) returns void { print(x); }" +
            "test(5); print(x);"
        );
        assertEquals(List.of("5", "10"), r.output);
    }

    @Test
    @DisplayName("Routine: Fibonacci sequence calculation")
    void routineFibonacci() {
        TestHelper.Result r = TestHelper.run(
            "routine fib(int n) returns int {" +
            "  if(n <= 1) return n;" +
            "  return fib(n-1) + fib(n-2);" +
            "}" +
            "print(fib(6));"
        );
        assertEquals(List.of("8"), r.output);
    }

    @Test
    @DisplayName("Routine: Return from deeply nested if blocks")
    void routineDeepReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine deep() returns int {" +
            "  if(true) { if(true) { if(true) return 42; } }" +
            "  return 0;" +
            "}" +
            "print(deep());"
        );
        assertEquals(List.of("42"), r.output);
    }

    @Test
    @DisplayName("Routine: While loop with return inside")
    void routineWhileReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine find(int limit) returns int {" +
            "  int x = 0;" +
            "  while(true) {" +
            "    if(x == limit) return x;" +
            "    x++;" +
            "  }" +
            "}" +
            "print(find(3));"
        );
        assertEquals(List.of("3"), r.output);
    }

    @Test
    @DisplayName("Routine: Loop with return inside")
    void routineLoopReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine search() returns int {" +
            "  loop(int i from 1 to 10) {" +
            "    if(i == 7) return i;" +
            "  }" +
            "  return 0;" +
            "}" +
            "print(search());"
        );
        assertEquals(List.of("7"), r.output);
    }

    @Test
    @DisplayName("Routine: Infinite while with conditional returns")
    void routineWhileMultiReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine choice(bool b) returns string {" +
            "  while(true) {" +
            "    if(b) return \"yes\";" +
            "    else return \"no\";" +
            "  }" +
            "}" +
            "print(choice(false));"
        );
        assertEquals(List.of("no"), r.output);
    }

    @Test
    @DisplayName("Routine: Returning bool from comparison")
    void routineBoolReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine isEven(int n) returns bool { return n % 2 == 0; }" +
            "print(isEven(4));"
        );
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("Routine: Conditional return in all branches (signum)")
    void routineSignumBranches() {
        TestHelper.Result r = TestHelper.run(
            "routine sign(int n) returns string {" +
            "  if(n > 0) return \"pos\";" +
            "  else if(n < 0) return \"neg\";" +
            "  else return \"zero\";" +
            "}" +
            "print(sign(0));"
        );
        assertEquals(List.of("zero"), r.output);
    }

    @Test
    @DisplayName("Routine: Modification of global state via side effect")
    void routineGlobalMutation() {
        TestHelper.Result r = TestHelper.run(
            "int g = 0;" +
            "routine mutate() returns void { g = g + 10; }" +
            "mutate(); mutate(); print(g);"
        );
        assertEquals(List.of("20"), r.output);
    }

    @Test
    @DisplayName("Routine: Complex expression as return value")
    void routineComplexMathReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine calc(int x, int y) returns int { return (x * y) + (100 / (x + y)); }" +
            "print(calc(2, 3));"
        );
        assertEquals(List.of("26"), r.output);
    }

    @Test
    @DisplayName("Routine: Loop boundary determined by routine call")
    void routineLoopBoundaryCall() {
        TestHelper.Result r = TestHelper.run(
            "routine getEnd() returns int { return 3; }" +
            "loop(int i from 1 to getEnd()) { print(i); }"
        );
        assertEquals(List.of("1", "2", "3"), r.output);
    }

    @Test
    @DisplayName("Routine: While loop condition as routine call")
    void routineWhileConditionCall() {
        TestHelper.Result r = TestHelper.run(
            "int x = 0;" +
            "routine shouldRun(int val) returns bool { return val < 2; }" +
            "while(shouldRun(x)) { x++; print(x); }"
        );
        assertEquals(List.of("1", "2"), r.output);
    }

    @Test
    @DisplayName("Routine: Void routine with early return")
    void routineVoidEarlyReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine skip(bool b) returns void {" +
            "  if(b) return;" +
            "  print(\"after\");" +
            "}" +
            "skip(true); skip(false);"
        );
        assertEquals(List.of("after"), r.output);
    }

    @Test
    @DisplayName("Routine: Nested calls result as argument")
    void routineNestedCallsAsArgs() {
        TestHelper.Result r = TestHelper.run(
            "routine inc(int n) returns int { return n + 1; }" +
            "print(inc(inc(inc(0))));"
        );
        assertEquals(List.of("3"), r.output);
    }

    @Test
    @DisplayName("Error: Assignment in while condition")
    void errorAssignmentInWhile() {
        TestHelper.Result r = TestHelper.run(
            "routine err() returns void { int x = 0; while(x = 1) { break; } } err();"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Error: Returning string in int routine")
    void errorWrongReturnType() {
        TestHelper.Result r = TestHelper.run(
            "routine bad() returns int { return \"string\"; } bad();"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Error: Missing return statement in int routine")
    void errorMissingReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine noRet() returns int { int x = 1; } noRet();"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Loop inside while inside routine")
    void routineNestedIterators() {
        TestHelper.Result r = TestHelper.run(
            "routine nested() returns int {" +
            "  int count = 0;" +
            "  int x = 0;" +
            "  while(x < 2) {" +
            "    loop(int i from 1 to 2) { count = count + 1; }" +
            "    x++;" +
            "  }" +
            "  return count;" +
            "}" +
            "print(nested());"
        );
        assertEquals(List.of("4"), r.output);
    }

    @Test
    @DisplayName("Routine: Recursion depth test")
    void routineRecursionDepth() {
        TestHelper.Result r = TestHelper.run(
            "routine countdown(int n) returns void {" +
            "  if(n == 0) { print(\"blastoff\"); return; }" +
            "  countdown(n - 1);" +
            "}" +
            "countdown(5);"
        );
        assertEquals(List.of("blastoff"), r.output);
    }

    @Test
    @DisplayName("Routine: String return with math evaluation inside")
    void routineStringMath() {
        TestHelper.Result r = TestHelper.run(
            "routine getVal() returns int { return 10 + 20; }" +
            "print(\"Result: \" + getVal());"
        );
        assertEquals(List.of("Result: 30"), r.output);
    }

    @Test
    @DisplayName("Routine: Return within a loop within an if")
    void routineDeepLogicReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine logic(int a) returns string {" +
            "  if(a > 0) {" +
            "    loop(int i from 1 to 5) { if(i == a) return \"found\"; }" +
            "  }" +
            "  return \"none\";" +
            "}" +
            "print(logic(3)); print(logic(10));"
        );
        assertEquals(List.of("found", "none"), r.output);
    }

    @Test
    @DisplayName("Routine: Multiple parameters math")
    void routineMultiParamMath() {
        TestHelper.Result r = TestHelper.run(
            "routine poly(int x, int y, int z) returns int { return x*x + y - z; }" +
            "print(poly(3, 4, 2));"
        );
        assertEquals(List.of("11"), r.output);
    }

    @Test
    @DisplayName("Routine: Boolean logic in return")
    void routineBoolLogicReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine check(int x) returns bool { return x > 0 and x < 10; }" +
            "print(check(5)); print(check(15));"
        );
        assertEquals(List.of("true", "false"), r.output);
    }

    @Test
    @DisplayName("Error: Calling routine with too few arguments")
    void errorTooFewArgs() {
        TestHelper.Result r = TestHelper.run(
            "routine f(int a, int b) returns void {} f(1);"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Increment global in recursive call")
    void routineRecursiveGlobalInc() {
        TestHelper.Result r = TestHelper.run(
            "int g = 0;" +
            "routine rec(int n) returns void {" +
            "  if(n == 0) return;" +
            "  g++;" +
            "  rec(n - 1);" +
            "}" +
            "rec(5); print(g);"
        );
        assertEquals(List.of("5"), r.output);
    }

    @Test
    @DisplayName("Routine: Loop iterator name same as global (shadowing)")
    void routineLoopShadowGlobal() {
        TestHelper.Result r = TestHelper.run(
            "int i = 99;" +
            "routine shadow() returns void {" +
            "  loop(int i from 1 to 2) { print(i); }" +
            "}" +
            "shadow(); print(i);"
        );
        assertEquals(List.of("1", "2", "99"), r.output);
    }

    @Test
    @DisplayName("Routine: Return from while with continue")
    void routineWhileContinueReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine logic() returns int {" +
            "  int x = 0;" +
            "  while(x < 5) {" +
            "    x++;" +
            "    if(x < 3) continue;" +
            "    return x;" +
            "  }" +
            "  return -1;" +
            "}" +
            "print(logic());"
        );
        assertEquals(List.of("3"), r.output);
    }

    @Test
    @DisplayName("Routine: Float return and precision")
    void routineFloatReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine getPi() returns float { return 3.14; }" +
            "print(getPi());"
        );
        assertEquals(List.of("3.14"), r.output);
    }

    @Test
    @DisplayName("Routine: Return result of callStmt")
    void routineReturnFuncResult() {
        TestHelper.Result r = TestHelper.run(
            "routine inner() returns int { return 100; }" +
            "routine outer() returns int { return inner(); }" +
            "print(outer());"
        );
        assertEquals(List.of("100"), r.output);
    }

    @Test
    @DisplayName("Error: Non-void routine ends without return after if-else")
    void errorPartialReturnPaths() {
        TestHelper.Result r = TestHelper.run(
            "routine partial(int x) returns int {" +
            "  if(x > 0) return 1;" +
            "} partial(0);"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Long chain of additions in return")
    void routineLongReturnExpr() {
        TestHelper.Result r = TestHelper.run(
            "routine sum() returns int { return 1 + 1 + 1 + 1 + 1; }" +
            "print(sum());"
        );
        assertEquals(List.of("5"), r.output);
    }

    @Test
    @DisplayName("Routine: Void return is implicit at end of block")
    void routineImplicitVoidReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine test() returns void { print(\"done\"); }" +
            "test();"
        );
        assertTrue(r.isSuccess());
    }


    @Test
    @DisplayName("Routine: Loop with routine call in expressions")
    void routineLoopExprCalls() {
        TestHelper.Result r = TestHelper.run(
            "routine one() returns int { return 1; }" +
            "routine ten() returns int { return 10; }" +
            "loop(int i from one() to ten() / 2) { print(i); }"
        );
        assertEquals(List.of("1", "2", "3", "4", "5"), r.output);
    }

    @Test
    @DisplayName("Error: Declaring same parameter name twice")
    void errorDuplicateParams() {
        TestHelper.Result r = TestHelper.run(
            "routine dup(int a, int a) returns void {}"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Nested blocks return priority")
    void routineNestedBlocksReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine priority() returns int {" +
            "  { return 1; }" +
            "  return 2;" +
            "}" +
            "print(priority());"
        );
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("Routine: While loop false initially")
    void routineWhileFalseReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine never() returns int {" +
            "  while(false) { return 0; }" +
            "  return 1;" +
            "}" +
            "print(never());"
        );
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("Routine: Returning result of unary not")
    void routineNotReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine flip(bool b) returns bool { return not b; }" +
            "print(flip(true));"
        );
        assertEquals(List.of("false"), r.output);
    }

    @Test
    @DisplayName("Routine: Call in complex math expression")
    void routineCallInMath() {
        TestHelper.Result r = TestHelper.run(
            "routine val() returns int { return 5; }" +
            "print(10 + val() * 2);"
        );
        assertEquals(List.of("20"), r.output);
    }

    @Test
    @DisplayName("Routine: Variable assignment using routine call")
    void routineAssignFromCall() {
        TestHelper.Result r = TestHelper.run(
            "routine getVal() returns int { return 42; }" +
            "int x = getVal();" +
            "print(x);"
        );
        assertEquals(List.of("42"), r.output);
    }

    @Test
    @DisplayName("Error: Using return in top level (not in routine)")
    void errorReturnTopLevel() {
        TestHelper.Result r = TestHelper.run(
            "int x = 10; return x;"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Multi-line string return")
    void routineMultiLineString() {
        TestHelper.Result r = TestHelper.run(
            "routine getStr() returns string { return \"A\nB\"; }" +
            "getStr();"
        );
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Parameter modification inside (passed by value)")
    void routineParamByValue() {
        TestHelper.Result r = TestHelper.run(
            "routine change(int n) returns void { n = 10; }" +
            "int x = 5; change(x); print(x);"
        );
        assertEquals(List.of("5"), r.output);
    }


    @Test
    @DisplayName("Routine: While loop with pre-increment in condition")
    void routineWhilePreIncReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine preInc() returns int {" +
            "  int x = 0;" +
            "  while(++x < 5) { if(x == 3) return x; }" +
            "  return 0;" +
            "}" +
            "print(preInc());"
        );
        assertEquals(List.of("3"), r.output);
    }

    @Test
    @DisplayName("Error: Routine return type mismatch on float-int conversion")
    void errorImplicitFloatToInt() {
        TestHelper.Result r = TestHelper.run(
            "routine getInt() returns int { return 3.14; }" +
            "getInt();"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: String multiplication/concat simulation")
    void routineStringWork() {
        TestHelper.Result r = TestHelper.run(
            "routine repeat(string s) returns string { return s + s; }" +
            "print(repeat(\"abc\"));"
        );
        assertEquals(List.of("abcabc"), r.output);
    }

    @Test
    @DisplayName("Routine: Recursive call in binary expression")
    void routineRecursiveBinary() {
        TestHelper.Result r = TestHelper.run(
            "routine sumN(int n) returns int {" +
            "  if(n <= 0) return 0;" +
            "  return n + sumN(n - 1);" +
            "}" +
            "print(sumN(4));"
        );
        assertEquals(List.of("10"), r.output);
    }

    @Test
    @DisplayName("Error: Calling non-existent routine")
    void errorMissingRoutine() {
        TestHelper.Result r = TestHelper.run(
            "ghost();"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Boolean true return in while condition")
    void routineWhileTrueCall() {
        TestHelper.Result r = TestHelper.run(
            "routine isTrue() returns bool { return true; }" +
            "int x = 0;" +
            "while(isTrue()) { x++; if(x == 2) break; }" +
            "print(x);"
        );
        assertEquals(List.of("2"), r.output);
    }

    @Test
    @DisplayName("Routine: Parenthesized return expression")
    void routineParenReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine paren() returns int { return (1 + 2) * 3; }" +
            "print(paren());"
        );
        assertEquals(List.of("9"), r.output);
    }

    @Test
    @DisplayName("Routine: Return from loop with mod check")
    void routineLoopModReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine firstEven(int start, int end) returns int {" +
            "  loop(int i from start to end) { if(i % 2 == 0) return i; }" +
            "  return -1;" +
            "}" +
            "print(firstEven(7, 10));"
        );
        assertEquals(List.of("8"), r.output);
    }

    @Test
    @DisplayName("Routine: Multiple calls with different parameters")
    void routineMultiCalls() {
        TestHelper.Result r = TestHelper.run(
            "routine f(int n) returns int { return n * n; }" +
            "print(f(2)); print(f(3));"
        );
        assertEquals(List.of("4", "9"), r.output);
    }

    @Test
    @DisplayName("Routine: Empty return in void routine within if")
    void routineVoidReturnInIf() {
        TestHelper.Result r = TestHelper.run(
            "routine check(bool b) returns void {" +
            "  if(b) { print(\"a\"); return; }" +
            "  print(\"b\");" +
            "}" +
            "check(true);"
        );
        assertEquals(List.of("a"), r.output);
    }

    @Test
    @DisplayName("Error: Function parameter name same as routine name")
    void errorParamShadowsRoutine() {
        TestHelper.Result r = TestHelper.run(
            "routine f(int f) returns void { print(f); } f(1);"
        );
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Mixed pre/post increment in return")
    void routineIncReturn() {
        TestHelper.Result r = TestHelper.run(
            "int x = 5;" +
            "routine get() returns int { return x++; }" +
            "print(get()); print(x);"
        );
        assertEquals(List.of("5", "6"), r.output);
    }

    @Test
    @DisplayName("Routine: Complex boolean logic chain")
    void routineComplexBool() {
        TestHelper.Result r = TestHelper.run(
            "routine logic(bool a, bool b) returns bool { return (a or b) and not (a and b); }" +
            "print(logic(true, false));"
        );
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("Routine: Return call result with different type (float to float)")
    void routineFloatChain() {
        TestHelper.Result r = TestHelper.run(
            "routine a() returns float { return 1.5; }" +
            "routine b() returns float { return a(); }" +
            "print(b());"
        );
        assertEquals(List.of("1.5"), r.output);
    }

    @Test
    @DisplayName("Error: Duplicate parameter types but same names")
    void errorDuplicateParamNames() {
        TestHelper.Result r = TestHelper.run(
            "routine test(int x, float x) returns void {}"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Void routine with block inside")
    void routineVoidBlock() {
        TestHelper.Result r = TestHelper.run(
            "routine test() returns void { { print(\"block\"); } }" +
            "test();"
        );
        assertEquals(List.of("block"), r.output);
    }

    @Test
    @DisplayName("Routine: Recursive factorial with 0 case")
    void routineFactZero() {
        TestHelper.Result r = TestHelper.run(
            "routine fact(int n) returns int { if(n == 0) return 1; return n * fact(n-1); }" +
            "print(fact(0));"
        );
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("Routine: Global variable increment in routine return")
    void routineSideEffectInReturn() {
        TestHelper.Result r = TestHelper.run(
            "int g = 0;" +
            "routine get() returns int { return g = g + 1; }" +
            "print(get());"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Correcting side effect test (no assignment in return)")
    void routineSideEffectCorrected() {
        TestHelper.Result r = TestHelper.run(
            "int g = 0;" +
            "routine get() returns int { g = g + 1; return g; }" +
            "print(get());"
        );
        assertEquals(List.of("1"), r.output);
    }

    @Test
    @DisplayName("Routine: Deeply nested routine calls with math")
    void routineMathChain() {
        TestHelper.Result r = TestHelper.run(
            "routine a(int x) returns int { return x + 1; }" +
            "routine b(int x) returns int { return a(x) * 2; }" +
            "routine c(int x) returns int { return b(x) - a(x); }" +
            "print(c(5));"
        );
        // b(5) = (5+1)*2 = 12. a(5) = 6. 12 - 6 = 6.
        assertEquals(List.of("6"), r.output);
    }

    @Test
    @DisplayName("Error: Routine returning note where int expected")
    void errorTypeNoteInt() {
        TestHelper.Result r = TestHelper.run(
            "routine get() returns int { return C4; }\n" +
            "get();"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Unary minus in return")
    void routineUnaryReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine neg(int x) returns int { return -x; }" +
            "print(neg(10));"
        );
        assertEquals(List.of("-10"), r.output);
    }

    @Test
    @DisplayName("Routine: While loop with complex expression return")
    void routineWhileExprReturn() {
        TestHelper.Result r = TestHelper.run(
            "routine test() returns int { int x = 0; while(x < 10) { if(x * x > 10) return x; x++; } return 0; }" +
            "print(test());"
        );
        // 4*4 = 16 > 10.
        assertEquals(List.of("4"), r.output);
    }

    @Test
    @DisplayName("Routine: Multiple statements after loop in routine")
    void routineFlowAfterLoop() {
        TestHelper.Result r = TestHelper.run(
            "routine flow() returns string { loop(int i from 1 to 2) { print(i); } return \"end\"; }" +
            "print(flow());"
        );
        assertEquals(List.of("1", "2", "end"), r.output);
    }

    @Test
    @DisplayName("Error: Parameter name same as type name")
    void errorParamNameType() {
        TestHelper.Result r = TestHelper.run(
            "routine test(int int) returns void {}"
        );
        assertFalse(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Boolean 'and' in if condition")
    void routineAndIf() {
        TestHelper.Result r = TestHelper.run(
            "routine both(bool a, bool b) returns void { if(a and b) print(\"yes\"); }" +
            "both(true, true);"
        );
        assertEquals(List.of("yes"), r.output);
    }

    @Test
    @DisplayName("Routine: Return from loop in track context (integration)")
    void routineTrackIntegration() {
        TestHelper.Result r = TestHelper.run(
            "routine getDur() returns int { return 4; }" +
            "track t1() { loop(int i from 1 to getDur()) { grid(1/4){C4} } }"
        );
        assertTrue(r.isSuccess());
    }

    @Test
    @DisplayName("Routine: Recursive call as part of math expression")
    void routineRecursiveMath() {
        TestHelper.Result r = TestHelper.run(
            "routine sum(int n) returns int { if(n <= 0) return 0; return n + sum(n-1); }" +
            "print(sum(3) + 10);"
        );
        // sum(3) = 6. 6 + 10 = 16.
        assertEquals(List.of("16"), r.output);
    }

    @Test
    @DisplayName("Final: Routine returning complex string result")
    void routineFinal() {
        TestHelper.Result r = TestHelper.run(
            "routine status(int s) returns string { if(s > 50) return \"pass\"; return \"fail\"; }" +
            "print(status(75));"
        );
        assertEquals(List.of("pass"), r.output);
    }
}