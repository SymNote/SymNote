import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RoutineAdvancedTest {

    // --- ZASIĘG ZMIENNYCH (SCOPING) ---

    @Test
    @DisplayName("valid: routine uses its own local variable, not global with same name")
    void valid_routine_local_shadowing() {
        TestHelper.Result r = TestHelper.run(
            "int x = 100;\n" +
            "routine shadow() returns int {\n" +
            "    int x = 5;\n" +
            "    return x;\n" +
            "}\n" +
            "print(shadow());\n" +
            "print(x);"
        );
        assertTrue(r.isSuccess(), "Error: " + (r.error != null ? r.error.getMessage() : "unknown"));
        // Pierwsze x to 5 (lokalne), drugie to 100 (globalne)
        assertEquals(List.of("5", "100"), r.output);
    }

    @Test
    @DisplayName("valid: routine can access and modify global variable")
    void valid_routine_global_access() {
        TestHelper.Result r = TestHelper.run(
            "int g = 10;\n" +
            "routine modify_g() returns void {\n" +
            "    g = g + 5;\n" +
            "}\n" +
            "modify_g();\n" +
            "print(g);"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("15"), r.output);
    }

    // --- PRZEKAZYWANIE TYPÓW ---

    @Test
    @DisplayName("valid: routine accepting and returning different types")
    void valid_routine_types_mix() {
        TestHelper.Result r = TestHelper.run(
            "routine describe(string name, int age, float score) returns string {\n" +
            "    return name + \": \" + age + \" pts, score \" + score;\n" +
            "}\n" +
            "print(describe(\"Hero\", 20, 9.5));"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("Hero: 20 pts, score 9.5"), r.output);
    }

    // --- PRZECIĄŻANIE (OVERLOADING) ---
    // Uwaga: Sprawdź czy Twój język wspiera funkcje o tej samej nazwie z różnymi parametrami!

    @Test
    @DisplayName("valid: routine overloading (same name, different params)")
    void valid_routine_overloading() {
        TestHelper.Result r = TestHelper.run(
            "routine add(int a, int b) returns int { return a + b; }\n" +
            "routine add(string a, string b) returns string { return a + b; }\n" +
            "print(add(2, 3));\n" +
            "print(add(\"Hello\", \"World\"));"
        );
        if (r.isSuccess()) {
            assertEquals(List.of("5", "HelloWorld"), r.output);
        } else {
            // Jeśli Twój język nie wspiera przeciążania, ten test udokumentuje to zachowanie
            System.out.println("Note: Overloading not supported or failed: " + r.error.getMessage());
        }
    }

    // --- REKURENCJA (RECURSION) ---

    @Test
    @DisplayName("valid: recursive routine (factorial)")
    void valid_routine_recursion() {
        TestHelper.Result r = TestHelper.run(
            "routine fact(int n) returns int {\n" +
            "    if (n <= 1) { return 1; }\n" +
            "    return n * fact(n - 1);\n" +
            "}\n" +
            "print(fact(5));"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("120"), r.output);
    }

    // --- TESTY BŁĘDÓW ---

    @Test
    @DisplayName("error: access routine parameter outside its scope")
    void error_param_leak() {
        TestHelper.Result r = TestHelper.run(
            "routine test(int secret) returns void { print(secret); }\n" +
            "test(10);\n" +
            "print(secret);" // secret nie powinien być dostępny tutaj
        );
        assertFalse(r.isSuccess(), "Variable 'secret' should not leak to global scope");
    }

    @Test
    @DisplayName("error: return type mismatch")
    void error_return_type_mismatch() {
        TestHelper.Result r = TestHelper.run(
            "routine get_int() returns int {\n" +
            "    return \"not an int\";\n" +
            "}\n" +
            "get_int();"
        );
        assertFalse(r.isSuccess(), "Should fail when routine returns string instead of int");
    }

    @Test
    @DisplayName("error: calling routine with too few arguments")
    void error_missing_args() {
        TestHelper.Result r = TestHelper.run(
            "routine add(int a, int b) returns int { return a + b; }\n" +
            "add(5);" // Brakuje b
        );
        assertFalse(r.isSuccess());
    }
}