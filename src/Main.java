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
            // 1. Load file from argument
            CharStream input = CharStreams.fromFileName(args[0]);
            // 2. Lexer and Parser Analysis
            SymNoteLexer lexer = new SymNoteLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SymNoteParser parser = new SymNoteParser(tokens);

            // 3. Build AST tree
            ParseTree tree = parser.program();

            // 4. Run Visitor
            MySymNoteVisitor visitor = new MySymNoteVisitor();
            visitor.visit(tree);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}