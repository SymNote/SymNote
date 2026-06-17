import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArchitectureSecurityTest {

    @Test
    @DisplayName("valid: loop should not be broken by 'BREAK' string evaluation")
    void valid_loop_string_trap() {
        String code = 
            "loop (int i from 1 to 3) {\n" +
            "    \"BREAK\";\n" +
            "    print(i);\n" +
            "}\n";
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertTrue(r.isSuccess(), "Script failed with error");
        assertEquals(List.of("1", "2", "3"), r.output);
    }

    @Test
    @DisplayName("valid: flat AST grid recursion does not throw StackOverflow")
    void valid_grid_flat_ast() {
        StringBuilder dashes = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            dashes.append("- ");
        }

        String code = 
            "synth TestSynth = load_synth(\"piano\");\n" +
            "track CrashTest() {\n" +
            "    use_synth(TestSynth);\n" +
            "    grid(1/16) { " + dashes.toString() + " }\n" +
            "}\n" +
            "CrashTest();\n" +
            "print(\"Survived\");";
            
        TestHelper.Result r = TestHelper.run(code);
        
        assertTrue(r.isSuccess(), "Script failed or threw StackOverflowException");
        assertEquals(List.of("Survived"), r.output);
    }
}