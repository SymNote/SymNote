import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Please provide a file path as an argument.");
                return;
            }
            CharStream input = CharStreams.fromFileName(args[0]);

            SymNoteLexer lexer = new SymNoteLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SymNoteParser parser = new SymNoteParser(tokens);

            lexer.removeErrorListeners();
            parser.removeErrorListeners();
            ErrorListener errorListener = new ErrorListener();
            lexer.addErrorListener(errorListener);
            parser.addErrorListener(errorListener);

            ParseTree tree = parser.program();

            if (errorListener.hasError) {
                System.err.println("Compilation aborted: The program contains syntax errors.");
            } else {
                SymNoteInterpreter interpreter = new SymNoteInterpreter();
                interpreter.visit(tree);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}