import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ComparisonTest {

    

    @Test
    @DisplayName("valid: int comparison 10 > 5")
    void valid_int_gt() {
        TestHelper.Result r = TestHelper.run("bool res = 10 > 5; print(res);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: float comparison 2.5 <= 3.0")
    void valid_float_le() {
        TestHelper.Result r = TestHelper.run("bool res = 2.5 <= 3.0; print(res);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: mixed type comparison int == float (implicit cast)")
    void valid_mixed_comparison() {
        
        TestHelper.Result r = TestHelper.run("bool res = (5 == 5.0); print(res);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: mixed type gt 5.1 > 5")
    void valid_mixed_gt() {
        TestHelper.Result r = TestHelper.run("bool res = 5.1 > 5; print(res);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("true"), r.output);
    }

    

    @Test
    @DisplayName("valid: string equality")
    void valid_string_eq() {
        TestHelper.Result r = TestHelper.run("bool res = (\"test\" == \"test\"); print(res);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("true"), r.output);
    }

    @Test
    @DisplayName("valid: bool equality true != false")
    void valid_bool_ne() {
        TestHelper.Result r = TestHelper.run("bool res = (true != false); print(res);");
        assertTrue(r.isSuccess());
        assertEquals(List.of("true"), r.output);
    }

    


    @Test
    @DisplayName("error: compare bool and int (invalid)")
    void error_bool_int_comparison() {
        TestHelper.Result r = TestHelper.run("bool res = (true == 1);");
        assertFalse(r.isSuccess(), "Should not allow comparing Bool with Int");
    }

    @Test
    @DisplayName("error: greater than operator on booleans (invalid)")
    void error_bool_gt() {
        
        TestHelper.Result r = TestHelper.run("bool res = (true > false);");
        assertFalse(r.isSuccess(), "Operator '>' should not be applicable to booleans");
    }

    

    @Test
    @DisplayName("valid: complex comparison chain")
    void valid_complex_expression() {
        TestHelper.Result r = TestHelper.run(
            "int a = 10; int b = 20; float c = 15.5; " +
            "bool res = (a < b) and (c > a) and (b != a); " +
            "print(res);"
        );
        assertTrue(r.isSuccess());
        assertEquals(List.of("true"), r.output);
    }
}