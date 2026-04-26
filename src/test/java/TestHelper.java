import gen.SymNoteLexer;
import gen.SymNoteParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

public class TestHelper {

    public static final class Result {
        public final List<String> output;
        public final Exception error;
        public final boolean hasSyntaxError;

        Result(List<String> output, Exception error, boolean hasSyntaxError) {
            this.output = output;
            this.error = error;
            this.hasSyntaxError = hasSyntaxError;
        }

        public boolean isSuccess() {
            return error == null && !hasSyntaxError;
        }
    }

    public static Result run(String source) {
        ListOutputCollector collector = new ListOutputCollector();

        SymNoteLexer lexer = new SymNoteLexer(CharStreams.fromString(source));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SymNoteParser parser = new SymNoteParser(tokens);

        lexer.removeErrorListeners();
        parser.removeErrorListeners();

        ErrorListener errorListener = new ErrorListener();
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        ParseTree tree = parser.program();

        if (errorListener.hasError) {
            return new Result(collector.getLines(), null, true);
        }

        SymNoteDeclarationListener declarationListener = new SymNoteDeclarationListener();
        try {
            ParseTreeWalker.DEFAULT.walk(declarationListener, tree);
        } catch (Exception e) {
            return new Result(collector.getLines(), e, false);
        }

        SymNoteInterpreter interpreter = new SymNoteInterpreter(
                declarationListener.getDeclaredVariables(), collector);
        try {
            interpreter.visit(tree);
        } catch (Exception e) {
            if (!collector.getLines().isEmpty()) {
                System.out.println("  [print output] " + collector.getLines());
            }
            return new Result(collector.getLines(), e, false);
        }

        if (!collector.getLines().isEmpty()) {
            System.out.println("  [print output] " + collector.getLines());
        }
        return new Result(collector.getLines(), null, false);
    }
}
