import java.util.Set;

public class MySymNoteVisitor extends SymNoteBaseVisitor<Object> {
    private static final Set<String> SYSTEM_FUNCTIONS = Set.of(
            "set_bpm",
            "set_vol",
            "load_sample",
            "load_synth",
            "use_synth"
    );

    @Override
    public Object visitProgram(SymNoteParser.ProgramContext ctx) {
        System.out.println("Visiting program");
        return super.visitProgram(ctx);
    }

    @Override
    public Object visitIfStmt(SymNoteParser.IfStmtContext ctx) {
        System.out.println("Visiting if statement");
        return super.visitIfStmt(ctx);
    }

     @Override
    public Object visitWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        System.out.println("Visiting while statement");
        return super.visitWhileStmt(ctx);
    }

     @Override
    public Object visitLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        System.out.println("Visiting loop statement");
        return super.visitLoopStmt(ctx);
    }

     @Override
    public Object visitParallelStmt(SymNoteParser.ParallelStmtContext ctx) {
        System.out.println("Visiting parallel statement");
        return super.visitParallelStmt(ctx);
    }

    @Override
    public Object visitCallExpr(SymNoteParser.CallExprContext ctx) {
        String functionName = ctx.ID().getText();

        if (SYSTEM_FUNCTIONS.contains(functionName)) {
            System.out.println("Visiting system function call: " + functionName);
            return null;
        }

        System.out.println("Visiting user function call: " + functionName);
        return super.visitCallExpr(ctx);
    }
}
