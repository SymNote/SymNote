import gen.SymNoteLexer;
import gen.SymNoteParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;



public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Please provide a file path as an argument.");
                return;
            }

            java.io.File sourceFile = new java.io.File(args[0]);
            if (!sourceFile.exists() || !sourceFile.isFile()) {
                System.err.println("Fatal Error: Could not locate SymNote file at '" + args[0] + "'");
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
                SymNoteDeclarationListener declarationListener = new SymNoteDeclarationListener();
                ParseTreeWalker.DEFAULT.walk(declarationListener, tree);

                SymNoteInterpreter interpreter = new SymNoteInterpreter(declarationListener.getDeclaredVariables());
                interpreter.visit(tree);

                AudioRenderer renderer = new JavaMidiRenderer();
                try {
                    renderer.init(interpreter.getBpm());
                    renderer.render(interpreter.getTimeline());
                } finally {
                    renderer.shutdown();
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}