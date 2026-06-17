import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MathLibraryTest {

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
    @DisplayName("valid: basic trigonometry (sin, cos)")
    void valid_trigonometry() {
        String code = 
            "print(sin(0.0));\n" +
            "print(cos(0.0));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("0.0", "1.0"), r.output);
    }

    @Test
    @DisplayName("valid: power and absolute value (pow, abs)")
    void valid_pow_abs() {
        String code = 
            "print(pow(2.0, 3.0));\n" +
            "print(abs(-15));\n" +
            "print(abs(-3.14));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("8.0", "15", "3.14"), r.output);
    }

    @Test
    @DisplayName("valid: random number generator bounds")
    void valid_rand() {
        String code = 
            "int r = rand(42, 42);\n" +
            "print(r);";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("42"), r.output);
    }

    @Test
    @DisplayName("valid: algorithmic functions (gcd, is_prime)")
    void valid_algorithmic() {
        String code = 
            "print(gcd(48, 18));\n" +
            "print(gcd(-10, 5));\n" +
            "print(is_prime(2));\n" +
            "print(is_prime(4));\n" +
            "print(is_prime(13));\n" +
            "print(is_prime(1));\n" +
            "print(is_prime(-5));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("6", "5", "true", "false", "true", "false", "false"), r.output);
    }

    @Test
    @DisplayName("error: wrong number of arguments (arity check)")
    void error_wrong_arity() {
        String code = "float x = sin(1.0, 2.0);";
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertFalse(r.isSuccess(), "Expected an error for wrong arity");
        assertNotNull(r.error, "Expected an exception to be thrown");
        assertTrue(r.error.getMessage().contains("requires exactly 1 argument"), 
            "Expected arity error message, but got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: wrong argument type for math function")
    void error_wrong_type() {
        String code = "int g = gcd(\"10\", 5);";
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertFalse(r.isSuccess(), "Expected an error for string argument");
        assertNotNull(r.error, "Expected an exception to be thrown");
        assertTrue(r.error.getMessage().contains("requires two 'int' arguments"), 
            "Expected type error message, but got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: rand() with min > max")
    void error_rand_invalid_bounds() {
        String code = "int r = rand(10, 5);";
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertFalse(r.isSuccess(), "Expected an error for invalid bounds");
        assertNotNull(r.error, "Expected an exception to be thrown");
        assertTrue(r.error.getMessage().contains("min cannot be greater than max"), 
            "Expected bounds error message, but got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("valid: musical functions (transpose, pitch_to_freq)")
    void valid_musical_functions() {
        String code = 
            "note n = transpose(C4, 2);\n" +
            "print(pitch_to_freq(A4));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("440.0"), r.output);
    }

    @Test
    @DisplayName("valid: algorithmic mapping (scale, clamp, avg)")
    void valid_mapping_functions() {
        String code = 
            "print(scale(0.5, 0.0, 1.0, 0.0, 100.0));\n" +
            "print(clamp(150.0, 0.0, 100.0));\n" +
            "print(avg(10, 20));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("50.0", "100.0", "15.0"), r.output);
    }

    @Test
    @DisplayName("valid: advanced algorithms (is_power_of_two, sum_digits)")
    void valid_advanced_algorithms() {
        String code = 
            "print(is_power_of_two(16));\n" +
            "print(is_power_of_two(18));\n" +
            "print(sum_digits(456));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("true", "false", "15"), r.output);
    }

    @Test
    @DisplayName("valid: fourier wave evaluation")
    void valid_fourier_wave() {
        String code = 
            "print(fourier_wave(0.0, 10));\n" +
            "print(round(fourier_wave(0.25, 1)));";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("0.0", "1"), r.output);
    }

    @Test
    @DisplayName("error: transpose out of bounds")
    void error_transpose_bounds() {
        String code = "note n = transpose(C4, -100);"; 
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertFalse(r.isSuccess(), "Expected an error");
        assertNotNull(r.error, "Expected an exception to be thrown");
        assertTrue(r.error.getMessage().contains("out of MIDI range"), "Got: " + r.error.getMessage());
    }

    @Test
    @DisplayName("error: pitch_to_freq invalid argument type")
    void error_pitch_to_freq_type() {
        String code = "float f = pitch_to_freq(\"A4\");"; 
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertFalse(r.isSuccess(), "Expected an error");
        assertNotNull(r.error, "Expected an exception to be thrown");
        assertTrue(r.error.getMessage().contains("requires (note, int) at line") || r.error.getMessage().contains("Type mismatch") || r.error.getMessage().toLowerCase().contains("note"), 
            "Got: " + r.error.getMessage());
    }
}