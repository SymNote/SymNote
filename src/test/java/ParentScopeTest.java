import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParentScopeTest {

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
    @DisplayName("valid: parent scope resolution example from requirements")
    void valid_parent_scope_resolution() {
        String code = 
            "int a = 1;\n" +
            "{\n" +
            "    int a = 5;\n" +
            "    int x = a;\n" +
            "    int y = parent::a;\n" +
            "    print(x);\n" +
            "    print(y);\n" +
            "}";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("5", "1"), r.output);
    }

    @Test
    @DisplayName("valid: nested multi-level parent scope resolution (parent::parent::a)")
    void valid_nested_multi_parent_scope() {
        String code = 
            "int a = 100;\n" +
            "{\n" +
            "    int a = 200;\n" +
            "    {\n" +
            "        int a = 300;\n" +
            "        int x = parent::a;\n" +
            "        int y = parent::parent::a;\n" +
            "        print(x);\n" +
            "        print(y);\n" +
            "    }\n" +
            "}";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("200", "100"), r.output);
    }

    @Test
    @DisplayName("valid: assignment to a parent scope variable")
    void valid_parent_scope_assignment() {
        String code = 
            "int a = 10;\n" +
            "{\n" +
            "    int a = 50;\n" +
            "    parent::a = 25;\n" +
            "    print(a);\n" +
            "}\n" +
            "print(a);";
            
        TestHelper.Result r = TestHelper.run(code);
        assertTrue(r.isSuccess(), "Expected no error but got: " + errorMsg(r));
        assertEquals(List.of("50", "25"), r.output);
    }
}