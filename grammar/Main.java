import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Wczytaj plik test.sn
            CharStream input = CharStreams.fromFileName("test.sn");
            // 2. Analiza (Lexer i Parser)
            SymNoteLexer lexer = new SymNoteLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SymNoteParser parser = new SymNoteParser(tokens);

            // 3. Zbuduj drzewo
            ParseTree tree = parser.program();

            // 4. Uruchom swojego Visitora
            MySymNoteVisitor visitor = new MySymNoteVisitor();
            visitor.visit(tree);

        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }
}