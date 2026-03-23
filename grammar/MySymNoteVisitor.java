public class MySymNoteVisitor extends SymNoteBaseVisitor<Object> {
    @Override
    public Object visitProgram(SymNoteParser.ProgramContext ctx) {
        System.out.println("Odwiedzam program");
        return super.visitProgram(ctx);
    }

    @Override
    public Object visitIfStmt(SymNoteParser.IfStmtContext ctx) {
        System.out.println("Odwiedzam instrukcję if");
        return super.visitIfStmt(ctx);
    }

     @Override
    public Object visitWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        System.out.println("Odwiedzam instrukcję while");
        return super.visitWhileStmt(ctx);
    }

     @Override
    public Object visitLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        System.out.println("Odwiedzam instrukcję loop");
        return super.visitLoopStmt(ctx);
    }

     @Override
    public Object visitParallelStmt(SymNoteParser.ParallelStmtContext ctx) {
        System.out.println("Odwiedzam instrukcję parallel");
        return super.visitParallelStmt(ctx);
    }
}
